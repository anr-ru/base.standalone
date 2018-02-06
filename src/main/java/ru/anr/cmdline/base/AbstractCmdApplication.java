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

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.core.JLineShellComponent;

import ru.anr.base.BaseParent;

/**
 * Prototype for an command line application entry point.
 * 
 * 
 * @author Alexey Romanchuk
 * @created Oct 29, 2014
 * 
 */
public abstract class AbstractCmdApplication extends BaseParent {

    /**
     * Original command-line arguments of the application
     */
    private static String[] arguments;

    /**
     * Base initialization. Used here to add some default settings for all
     * applications.
     * 
     * @param app
     *            Application instance
     * @param args
     *            Initial command-line arguments
     */
    public static void main(AbstractCmdApplication app, String... args) {

        arguments = ArrayUtils.clone(args);
        app.run(app.getClass(), args);
    }

    /**
     * Shell component class
     */
    private Class<? extends JLineShellComponent> shellClass;

    /**
     * Initialization of {@link Bootstrap} bean, which creates all necessary
     * configuration for 'shell'. The original arguments are passed to the bean
     * to perform a 'silent' script execution.
     * 
     * @return A new bean instance
     */
    @Bean(name = "bootstrap", initMethod = "init")
    public Bootstrap getBootstrap() {

        return new BootstrapImpl(shellClass, arguments);
    }

    /**
     * Run procedure with global default settings applied for all applications.
     * 
     * @param clazz
     *            Class of application module.
     * @param args
     *            Initial command-line arguments
     */
    protected void run(Class<?> clazz, String... args) {

        SpringApplication spring = new SpringApplication(clazz);

        initialize(spring);
        spring.run(args);
    }

    /**
     * Entry point for changing settings. Must be overriden for custom
     * initialization.
     * 
     * @param spring
     *            Sprting Boot Runner
     */
    protected void initialize(SpringApplication spring) {

        spring.setShowBanner(false); // disabled a default banner
        spring.setWebEnvironment(false);
    }

    /**
     * @param shellClass
     *            the shellClass to set
     */
    public void setShellClass(Class<? extends JLineShellComponent> shellClass) {

        this.shellClass = shellClass;
    }
}
