/**
 * 
 */
package ru.anr.cmdline.base;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import ru.anr.base.tests.BaseTestCase;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Tests for {@link HidePasswordShell}
 *
 *
 * @author Alexey Romanchuk
 * @created Apr 9, 2015
 *
 */
@ContextConfiguration(classes = HidePasswordShellTest.class)
@RunWith(MockitoJUnitRunner.class)
public class HidePasswordShellTest extends BaseTestCase {

    /**
     * Mocked file inside of the shell
     */
    @Mock
    private FileWriter file;

    /**
     * Object under the test
     */
    private HidePasswordShell shell;

    /**
     * {@inheritDoc}
     */
    @Override
    @Before
    public void setUp() {

        super.setUp();

        shell = new HidePasswordShell();

        Assert.assertNotNull(file);
        inject(shell, "fileLog", file);
    }

    /**
     * Use case: passed an argument without a password
     * 
     * @throws IOException
     *             Required by file interfaces
     */
    @Test
    public void testCommandWithoutPassword() throws IOException {

        shell.logCommandToOutput("create --walletname my-wallet");
        Mockito.verify(file).write("create --walletname my-wallet\n");
    }

    /**
     * Use case: passed an argument with a password
     * 
     * @throws IOException
     *             Required by file interfaces
     */
    @Test
    public void testCommandWithPassword() throws IOException {

        shell.logCommandToOutput("create --pwd password");
        Mockito.verify(file).write("create --pwd\n");

        Mockito.reset(file);

        shell.logCommandToOutput("create --password password");
        Mockito.verify(file).write("create --password\n");

        Mockito.reset(file);

        shell.logCommandToOutput("create --pin 1234");
        Mockito.verify(file).write("create --pin\n");
    }

}
