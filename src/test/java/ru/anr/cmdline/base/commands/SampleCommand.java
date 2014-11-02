/**
 * 
 */
package ru.anr.cmdline.base.commands;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;

import ru.anr.cmdline.utils.SimpleOsCommand;

/**
 * Sample command for test purpose.
 * 
 * 
 * @author Alexey Romanchuk
 * @created Oct 28, 2014
 * 
 */
public class SampleCommand implements CommandMarker {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(SampleCommand.class);

    /**
     * Processing of test command
     * 
     * @param location
     *            path to file
     * @return A result
     * 
     * @throws IOException
     *             If a problem during file reading occured
     */
    @CliCommand(value = "load", help = "Loading information")
    public String hello(
            @CliOption(key = { "location" }, mandatory = true, help = "File location") final String location)
            throws IOException {

        FileSystemResource file = new FileSystemResource(location);

        String rs = "error: file '" + location + "' not found";

        if (file.exists()) {
            rs = IOUtils.readLines(file.getInputStream()).toString();
        }

        logger.info("Location: {}", location);
        return rs;
    }

    /**
     * Run binary file
     * 
     * @return Result of execution specified os command
     * @throws IOException
     *             In case of execution errors
     */
    @CliCommand(value = "exec", help = "Running an external application")
    public String exec() throws IOException {

        SimpleOsCommand os = new SimpleOsCommand();
        return os.execute("java -version"); // for example
    }
}
