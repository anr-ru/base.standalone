package ru.anr.cmdline.base;

import jline.TerminalFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.TerminalSizeAware;
import org.springframework.shell.core.JLineShellComponent;
import ru.anr.base.BaseParent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This shell finds out password arguments (--pwd, --password and --pin) and removes the rest of the line avoiding
 * storing these sensitive information in log files.
 *
 * @author Alexey Romanchuk
 * @created Apr 9, 2015
 *
 */

public class HidePasswordShell extends JLineShellComponent {

    /**
     * Regexp pattern to hide password from logs
     */
    private final Pattern regexp = Pattern.compile("(\\w.*)(--pwd|--password|--pin)");

    /**
     * {@inheritDoc}
     */
    @Override
    protected void logCommandToOutput(String processedLine) {

        Matcher m = regexp.matcher(processedLine);

        if (m.find()) {
            super.logCommandToOutput(m.group(1) + m.group(2));
        } else {
            super.logCommandToOutput(processedLine);
        }
    }

    /**
     * An overridden version of the method for printing the execution result. Use the standard console output instead of
     * logger which needs for command line environment executions.
     *
     * @param result The command result
     */
    protected void handleExecutionResult(Object result) {
        if (result instanceof Iterable<?>) {
            for (Object o : (Iterable<?>) result) {
                handleExecutionResult(o);
            }
        } else if (result instanceof TerminalSizeAware) {
            int width = TerminalFactory.get().getWidth();
            System.out.print(((TerminalSizeAware) result).render(width).toString() + "\n");
        } else {
            System.out.print(result.toString() + "\n");
        }
    }

    public void handleError(Throwable ex) {
        // Outputs to 'stdout' to catch in cmd environments
        System.out.println(BaseParent.nullSafe(ex, Throwable::getMessage));
    }
}
