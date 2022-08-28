package ru.anr.base.cmdline.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.shell.MethodTarget;
import ru.anr.base.cmdline.Application;
import ru.anr.base.cmdline.AbstractShellTestCase;

/**
 * A simple test to check the shell works.
 *
 * @author Alexey Romanchuk
 * @created Oct 28, 2014
 */
@Import(Application.class)
public class SamplesCommandTest extends AbstractShellTestCase {

    /**
     * Use case: sample load command
     */
    @Test
    public void sampleCheck() {

        final MethodTarget commandTarget = lookupCommand(shell, "load");
        Assertions.assertNotNull(commandTarget);
        Assertions.assertEquals("error: file 'New' not found", shell.evaluate(() -> "load XXX --location New"));
    }

    /**
     * Use case: sample exec command
     */
    @Test
    public void sampleExec() {

        assertContains(shell.evaluate(() -> "load").toString(),
                "Parameter '--text string' should be specified");
    }
}
