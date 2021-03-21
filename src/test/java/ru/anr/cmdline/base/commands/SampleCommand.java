/**
 *
 */
package ru.anr.cmdline.base.commands;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.anr.cmdline.base.custom.BaseCommand;

import java.io.IOException;

/**
 * Sample command for test purpose.
 *
 * @author Alexey Romanchuk
 * @created Oct 28, 2014
 *
 */
@ShellComponent("Samples")
public class SampleCommand extends BaseCommand {

    private static final Logger logger = LoggerFactory.getLogger(SampleCommand.class);

    @ShellMethod(key = "load", value = "Loading information")
    public String load(@ShellOption final String text,
                       @ShellOption(value = {"--location", "-l"}) final String location)
            throws IOException {

        FileSystemResource file = new FileSystemResource(location);

        String rs = "error: file '" + location + "' not found";

        if (file.exists()) {
            rs = IOUtils.readLines(file.getInputStream(), DEFAULT_CHARSET).toString();
        }

        logger.info("Text: {}, location: {}", text, location);
        return rs;
    }
}
