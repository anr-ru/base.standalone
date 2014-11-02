/**
 * 
 */
package ru.anr.cmdline.base.plugins;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.HistoryFileNameProvider;
import org.springframework.stereotype.Component;

/**
 * Privider for history file name.
 * 
 * 
 * @author Alexey Romanchuk
 * @created Oct 28, 2014
 * 
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomHistoryFileNameProvider implements HistoryFileNameProvider {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHistoryFileName() {

        return "shell-cmd-history.log";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProviderName() {

        return "history";
    }
}
