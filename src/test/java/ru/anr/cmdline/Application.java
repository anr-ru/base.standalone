/**
 * 
 */
package ru.anr.cmdline;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import ru.anr.cmdline.base.AbstractCmdApplication;

/**
 * Description ...
 *
 *
 * @author Alexey Romanchuk
 * @created Apr 7, 2015
 *
 */
@Configuration
@ImportResource("classpath:tests-plugins.xml")
public class Application extends AbstractCmdApplication {

    /**
     * Main entry point
     * 
     * @param args
     *            Command line args
     */
    public static void main(String... args) {

        AbstractCmdApplication.main(new Application(), args);
    }
}
