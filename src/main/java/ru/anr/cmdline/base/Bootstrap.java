/**
 * 
 */
package ru.anr.cmdline.base;

import org.springframework.shell.core.ExitShellRequest;
import org.springframework.shell.core.JLineShellComponent;

/**
 * An interface for {@link BootstrapImpl} bean.
 * 
 * 
 * @author Alexey Romanchuk
 * @created Oct 30, 2014
 * 
 */

public interface Bootstrap {

    /**
     * Start a 'shell' thread
     * 
     * @return Status of execution (exit code)
     */
    ExitShellRequest run();

    /**
     * Getting a direct 'shell' access
     * 
     * @return 'Shell' bean instance
     */
    JLineShellComponent getJLineShellComponent();
}
