/**
 * 
 */
package ru.anr.cmdline.base;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.commands.ConsoleCommands;
import org.springframework.shell.commands.ExitCommands;
import org.springframework.shell.commands.HelpCommands;
import org.springframework.shell.commands.InlineCommentCommands;
import org.springframework.shell.commands.ScriptCommands;

import ru.anr.cmdline.base.plugins.CustomBannerProvider;

/**
 * A configuration for used plugins and extensions.
 *
 * @author Alexey Romanchuk
 * @created Apr 7, 2015
 *
 */
@Configuration
@ComponentScan(basePackageClasses = CustomBannerProvider.class)
public class CmdAppConfig {

    @Bean
    public ConsoleCommands consoleCommands() {
        return new ConsoleCommands();
    }

    @Bean
    public ExitCommands exitCommands() {
        return new ExitCommands();
    }

    @Bean
    public HelpCommands helpCommands() {
        return new HelpCommands();
    }

    @Bean
    public InlineCommentCommands inlineCommentCommands() {
        return new InlineCommentCommands();
    }

    @Bean
    public ScriptCommands scriptCommands() {
        return new ScriptCommands();
    }

    @Bean
    public CmdLineParser parser() {
        return new CmdLineParser();
    }
}
