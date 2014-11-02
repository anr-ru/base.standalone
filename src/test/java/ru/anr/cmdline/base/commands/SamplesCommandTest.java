package ru.anr.cmdline.base.commands;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.shell.core.CommandResult;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.anr.cmdline.base.AbstractShellTestCase;

/**
 * A simple test to check the shell works.
 * 
 * 
 * @author Alexey Romanchuk
 * @created Oct 28, 2014
 * 
 */
@Configurable
@ContextConfiguration(locations = "classpath*:/tests-plugins.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SamplesCommandTest extends AbstractShellTestCase {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(SamplesCommandTest.class);

    /**
     * Use case: sample load command
     */
    @Test
    public void sampleCheck() {

        CommandResult r = getShell().executeCommand("load --location 'New York'");

        logger.info("Result: {}", r.getResult());
        Assert.assertEquals("error: file 'New York' not found", r.getResult());
    }

    /**
     * Use case: sample exec command
     */
    @Test
    public void sampleExec() {

        CommandResult r = getShell().executeCommand("exec");

        logger.info("Result: {}", r.getResult());
        Assert.assertNotNull(r.getResult());
        Assert.assertTrue(r.getResult().toString().contains("Java(TM) SE Runtime Environment"));
    }

    /**
     * Use case: check some standard commands are switched off
     */
    @Test
    public void checkNoSomeStandardCmd() {

        CommandResult r = getShell().executeCommand("version");
        Assert.assertFalse(r.isSuccess());

        r = getShell().executeCommand("cls");
        Assert.assertTrue(r.isSuccess());
    }
}
