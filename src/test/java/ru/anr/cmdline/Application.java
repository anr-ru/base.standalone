package ru.anr.cmdline;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import ru.anr.cmdline.base.AbstractCmdApplication;
import ru.anr.cmdline.base.HidePasswordShell;

/**
 * Sample application configuration.
 *
 * @author Alexey Romanchuk
 * @created Apr 7, 2015
 *
 */
@Configuration
@ImportResource("classpath:tests-app.xml")
public class Application extends AbstractCmdApplication {

    /**
     * Main entry point
     * 
     * @param args
     *            Command line args
     */
    public static void main(String... args) {
        Application.main(new Application(), HidePasswordShell.class, args);
    }
}
