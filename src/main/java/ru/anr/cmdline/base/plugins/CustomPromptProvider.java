/**
 * 
 */
package ru.anr.cmdline.base.plugins;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.PromptProvider;
import org.springframework.stereotype.Component;

/**
 * Prompt Provider implementation.
 * 
 * 
 * @author Alexey Romanchuk
 * @created Oct 28, 2014
 * 
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomPromptProvider implements PromptProvider {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProviderName() {

        return "Prompt";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPrompt() {

        /*
         * TODO: put $USER, $HOST variables here
         */
        return "cmd-shell>: ";
    }
}
