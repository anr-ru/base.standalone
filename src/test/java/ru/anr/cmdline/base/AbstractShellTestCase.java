package ru.anr.cmdline.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.shell.CommandRegistry;
import org.springframework.shell.MethodTarget;
import org.springframework.shell.Shell;
import org.springframework.util.ReflectionUtils;
import ru.anr.base.tests.BaseTestCase;

import javax.validation.constraints.NotNull;

/**
 * Abstract class used as a parent for all shell tests.
 *
 * @author Alexey Romanchuk
 * @created Oct 28, 2014
 */
@Import(AbstractCmdApplication.class)
public class AbstractShellTestCase extends BaseTestCase {

    @Autowired
    protected Shell shell;

    /**
     * Direct invocation of the command method for unit tests.
     *
     * @param methodTarget The method
     * @param args         The arguments to pass
     * @param <T>          The resulted value class
     * @return The result value
     */
    protected <T> T invoke(final MethodTarget methodTarget, final Object... args) {
        return (T) ReflectionUtils.invokeMethod(methodTarget.getMethod(), methodTarget.getBean(), args);
    }

    /**
     * Pulling the command implementation for testing.
     *
     * @param registry The command registry.
     * @param command  The command name
     * @return The method of the command
     */
    protected MethodTarget lookupCommand(@NotNull final CommandRegistry registry,
                                         @NotNull final String command) {
        return registry.listCommands().get(command);
    }
}
