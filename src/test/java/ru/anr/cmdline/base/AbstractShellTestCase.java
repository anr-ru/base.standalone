package ru.anr.cmdline.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.core.JLineShellComponent;

import ru.anr.base.tests.BaseTestCase;

/**
 * Abstract class used as a parent for all shell tests.
 * 
 * @author Alexey Romanchuk
 * @created Oct 28, 2014
 * 
 */
@Configurable
public class AbstractShellTestCase extends BaseTestCase {

    /**
     * A reference to a spring context
     */
    @Autowired
    private ApplicationContext ctx;

    /**
     * @return the shell
     */
    public JLineShellComponent getShell() {

        return ctx.getBean("shell", JLineShellComponent.class);
    }
}
