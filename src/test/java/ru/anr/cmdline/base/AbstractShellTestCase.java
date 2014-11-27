package ru.anr.cmdline.base;

import org.junit.Ignore;
import org.springframework.shell.core.JLineShellComponent;

import ru.anr.base.tests.BaseTestCase;

/**
 * Abstract class used as a parent for all shell tests.
 * 
 * @author Alexey Romanchuk
 * @created Oct 28, 2014
 * 
 */
@Ignore
public class AbstractShellTestCase extends BaseTestCase {

    /**
     * @return the shell
     */
    public JLineShellComponent getShell() {

        return bean("shell", JLineShellComponent.class);
    }
}
