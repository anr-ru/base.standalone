/**
 * 
 */
package ru.anr.cmdline.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.shell.core.ExitShellRequest;

import ru.anr.base.services.BaseServiceImpl;

/**
 * An application specific handler, which starts a command line shell.
 * 
 * @author Alexey Romanchuk
 * @created Oct 28, 2014
 * 
 */
public class CmdLineParser extends BaseServiceImpl implements CommandLineRunner {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(CmdLineParser.class);

    /**
     * {@link Bootstrap} bean
     */
    @Autowired
    @Qualifier("bootstrap")
    private Bootstrap bootstrap;

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(String... args) throws Exception {

        logger.debug("Initial command line args: {}", list(args));

        ExitShellRequest exitShellRequest = bootstrap.run();
        logger.info("Shell exit code: {}", exitShellRequest.getExitCode());
    }
}
