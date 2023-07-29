/*
 * Copyright 2014-2023 the original author or authors.
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
package ru.anr.base.cmdline;

import org.jline.builtins.Commands;
import org.jline.reader.History;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.Parser;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.shell.InputProvider;
import org.springframework.shell.SpringShellAutoConfiguration;
import org.springframework.shell.jcommander.JCommanderParameterResolverAutoConfiguration;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.JLineShellAutoConfiguration;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.shell.standard.FileValueProvider;
import org.springframework.shell.standard.StandardAPIAutoConfiguration;
import org.springframework.shell.standard.commands.StandardCommandsAutoConfiguration;
import ru.anr.base.BaseParent;
import ru.anr.base.cmdline.custom.InputReader;
import ru.anr.base.cmdline.custom.ProgressBar;
import ru.anr.base.cmdline.custom.ProgressCounter;
import ru.anr.base.cmdline.custom.ShellService;

/**
 * The base application configuration.
 *
 * @author Alexey Romanchuk
 * @created Oct 29, 2014
 */
@Import({
        // Core runtime
        SpringShellAutoConfiguration.class,
        JLineShellAutoConfiguration.class,
        // Various Resolvers
        JCommanderParameterResolverAutoConfiguration.class,
        StandardAPIAutoConfiguration.class,
        // Built-In Commands
        StandardCommandsAutoConfiguration.class,
        // Allows ${} support
        PropertyPlaceholderAutoConfiguration.class,
        // Sample Commands
        Commands.class,
        FileValueProvider.class
})
@Configuration
public class AbstractCmdApplication extends BaseParent {

    /**
     * The main entry point. In case of a need of single run and exit, please use the following
     * format: java -jar  name.jar @./file.txt
     * <p>
     * Where file.txt is a script file with commands inside.
     *
     * @param args The command line arguments
     */
    public static void main(String... args) {
        new AbstractCmdApplication().run(AbstractCmdApplication.class, args);
    }

    ////  Command line configurations, that can be overridden

    @Bean
    public ShellService shellService(@Lazy Terminal terminal) {
        return new ShellService(terminal);
    }

    @Bean("prompt")
    public PromptProvider getPromptProvider() {
        return () -> new AttributedString("CLI:>",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE)
        );
    }

    @Bean
    public InputReader inputReader(
            @Lazy Terminal terminal,
            @Lazy Parser parser,
            JLineShellAutoConfiguration.CompleterAdapter completer,
            @Lazy History history,
            ShellService shellService) {
        LineReaderBuilder lineReaderBuilder = LineReaderBuilder.builder()
                .terminal(terminal)
                .completer(completer)
                .history(history)
                .highlighter((LineReader reader, String buffer) ->
                        new AttributedString(buffer,
                                AttributedStyle.BOLD.foreground(
                                        ShellService.PromptColor.WHITE.toJlineAttributedStyle()))
                ).parser(parser);

        LineReader lineReader = lineReaderBuilder.build();
        lineReader.unsetOpt(LineReader.Option.INSERT_TAB);
        return new InputReader(lineReader, shellService);
    }

    @Bean
    public ProgressBar progressBar(ShellService shellService) {
        return new ProgressBar(shellService);
    }

    @Bean
    public ProgressCounter progressCounter(@Lazy Terminal terminal) {
        return new ProgressCounter(terminal);
    }

    @Bean
    @Autowired
    public InputProvider inputProvider(LineReader lineReader,
                                       @Qualifier("prompt") PromptProvider promptProvider) {
        return new InteractiveShellApplicationRunner.JLineInputProvider(lineReader, promptProvider);
    }

    /**
     * Runs the application with global default settings applied for all applications.
     *
     * @param clazz Class of application module.
     * @param args  Initial command-line arguments
     */
    protected void run(Class<?> clazz, String... args) {
        SpringApplication app = new SpringApplication(clazz);
        initialize(app);

        AbstractApplicationContext context = (AbstractApplicationContext) app.run(args);
        context.registerShutdownHook();
    }

    /**
     * The entry point for initializing settings. Must be overridden for a custom
     * initialization.
     *
     * @param spring Spring Boot Runner
     */
    protected void initialize(SpringApplication spring) {
        spring.setBannerMode(Banner.Mode.OFF);
    }
}
