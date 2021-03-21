package ru.anr.cmdline;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.anr.cmdline.base.AbstractCmdApplication;
import ru.anr.cmdline.base.commands.SampleCommand;
import ru.anr.cmdline.base.custom.DemoCommands;

/**
 * Sample application configuration.
 *
 * @author Alexey Romanchuk
 * @created Apr 7, 2015
 */
@Import({SampleCommand.class, DemoCommands.class})
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
