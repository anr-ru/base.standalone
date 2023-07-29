package ru.anr.base.cmdline;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.anr.base.cmdline.commands.SampleCommand;
import ru.anr.base.cmdline.custom.BatchRunnerConfiguration;
import ru.anr.base.cmdline.custom.DemoCommands;

/**
 * Sample application configuration.
 *
 * @author Alexey Romanchuk
 * @created Apr 7, 2015
 */
@Import({SampleCommand.class, DemoCommands.class, BatchRunnerConfiguration.class})
@Configuration
public class Application extends AbstractCmdApplication {

    /**
     * Main entry point
     *
     * @param args Command line args
     */
    public static void main(String... args) {
        new Application().run(Application.class, args);
    }
}
