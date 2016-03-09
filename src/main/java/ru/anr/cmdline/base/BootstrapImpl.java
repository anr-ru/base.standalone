/*
 * Copyright 2014 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package ru.anr.cmdline.base;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Import;
import org.springframework.shell.CommandLine;
import org.springframework.shell.ShellException;
import org.springframework.shell.SimpleShellCommandLineOptions;
import org.springframework.shell.core.ExitShellRequest;
import org.springframework.shell.core.JLineShellComponent;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import ru.anr.base.services.BaseServiceImpl;
import ru.anr.base.services.MessagePropertiesConfig;

/**
 * An implementation of shell bootstraping. It's rewritten original
 * {@link org.springframework.shell.Bootstrap} class from the spring-shell
 * project.
 * 
 * The reason to rewrite was mostly in inconvenient design practice used in
 * {@link org.springframework.shell.Bootstrap} and bad opportunities for
 * extensions.
 * 
 * 
 * @author Alexey Romanchuk
 * @created Oct 30, 2014
 * 
 */
@Import(MessagePropertiesConfig.class)
public class BootstrapImpl extends BaseServiceImpl implements Bootstrap {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(BootstrapImpl.class);

    /**
     * A commmand line storage (prototype for bean)
     */
    private CommandLine commandLine;

    /**
     * Empty constructor (the case when no command line args are provided).
     */
    public BootstrapImpl() {

        this(null, (String[]) null);
    }

    /**
     * Shell component class
     */
    private final Class<? extends JLineShellComponent> shellClass;

    /**
     * General constructor (original application's arguments are specified)
     * 
     * @param shellClass
     *            Class used for the shell component
     * @param args
     *            Command-line arguments
     */
    public BootstrapImpl(Class<? extends JLineShellComponent> shellClass, String... args) {

        super();
        try {

            this.shellClass = (shellClass == null) ? JLineShellComponent.class : shellClass;
            commandLine = SimpleShellCommandLineOptions.parseCommandLine(args);
        } catch (IOException e) {
            throw new ShellException(e.getMessage(), e);
        }
    }

    /**
     * Injection of a spring context
     */
    @Autowired
    private ConfigurableApplicationContext context;

    /**
     * Bean initialization. When starting, it tries to find out ant additional
     * plugins, which can be provided in a spring context.
     */
    @Override
    @PostConstruct
    public void init() {

        createAndRegisterBeanDefinition(context, shellClass, "shell");
        context.getBeanFactory().registerSingleton("commandLine", commandLine);

        // built-in commands and converters
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner((BeanDefinitionRegistry) context);
        scanner.scan("org.springframework.shell.converters", "org.springframework.shell.plugin.support");

        getJLineShellComponent().setHistorySize(Integer.MAX_VALUE);
        getJLineShellComponent().setDevelopmentMode(!isProdMode());
    }

    /**
     * Registration of beans.
     * 
     * @param ctx
     *            A spring context
     * @param clazz
     *            A bean class
     * @param name
     *            A name for bean
     */
    protected void createAndRegisterBeanDefinition(ConfigurableApplicationContext ctx, Class<?> clazz, String name) {

        RootBeanDefinition rbd = new RootBeanDefinition();
        rbd.setBeanClass(clazz);
        DefaultListableBeanFactory bf = (DefaultListableBeanFactory) ctx.getBeanFactory();
        if (name == null) {
            bf.registerBeanDefinition(clazz.getSimpleName(), rbd);
        } else {
            bf.registerBeanDefinition(name, rbd);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ExitShellRequest run() {

        StopWatch sw = new StopWatch("Shell Watch");
        sw.start();

        String[] commandsToExecuteAndThenQuit = commandLine.getShellCommandsToExecute();
        // The shell is used
        JLineShellComponent shell = context.getBean("shell", JLineShellComponent.class);
        ExitShellRequest exitShellRequest;

        if (null == commandsToExecuteAndThenQuit) {
            shell.start();
            shell.promptLoop();
            exitShellRequest = shell.getExitShellRequest();
            if (exitShellRequest == null) {
                // shouldn't really happen, but we'll fallback to this anyway
                exitShellRequest = ExitShellRequest.NORMAL_EXIT;
            }
            shell.waitForComplete();

        } else {
            boolean successful = false;
            exitShellRequest = ExitShellRequest.FATAL_EXIT;

            for (String cmd : commandsToExecuteAndThenQuit) {
                successful = shell.executeCommand(cmd).isSuccess();
                if (!successful) {
                    break;
                }
            }

            // if all commands were successful, set the normal exit status
            if (successful) {
                exitShellRequest = ExitShellRequest.NORMAL_EXIT;
            }

        }

        sw.stop();

        if (shell.isDevelopmentMode()) {
            logger.info("Total execution time: {} ms", sw.getLastTaskTimeMillis());
        }
        return exitShellRequest;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JLineShellComponent getJLineShellComponent() {

        return context.getBean("shell", JLineShellComponent.class);
    }
}
