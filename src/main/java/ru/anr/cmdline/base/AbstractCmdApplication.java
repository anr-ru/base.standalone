/**
 * 
 */
package ru.anr.cmdline.base;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;

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
    private String[] arguments;

    /**
     * Base initialization. Used here to add some default settings for all
     * applications.
     * 
     * @param app
     *            Application instance
     * @param args
     *            Initial command-line arguments
     */
    public static void main(AbstractCmdApplication app, String[] args) {

        app.arguments = args;
        app.run(app.getClass(), args);
    }

    /**
     * Initialization of {@link Bootstrap} bean, which creates all necessery
     * configuration for 'shell'. The original arguments are passed to the bean
     * to perform a 'silent' script execution.
     * 
     * @return A new bean instance
     */
    @Bean(name = "bootstrap", initMethod = "init")
    public Bootstrap getBootstrap() {

        return new BootstrapImpl(this.arguments);
    }

    /**
     * Run procedure with global default settings applied for all applications.
     * 
     * @param clazz
     *            Class of application module.
     * @param args
     *            Initial command-line arguments
     */
    protected void run(Class<?> clazz, String[] args) {

        SpringApplication spring = new SpringApplication(clazz);

        initialize(spring);
        spring.run(args);
    }

    /**
     * Entry point for changing settings
     * 
     * @param spring
     *            Sprting Boot Runner
     */
    protected void initialize(SpringApplication spring) {

        spring.setShowBanner(false); // disabled a default banner
        spring.setWebEnvironment(false);
    }
}
