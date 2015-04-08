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
 * Java-based configuration for Shell.
 *
 *
 * @author Alexey Romanchuk
 * @created Apr 7, 2015
 *
 */
@Configuration
@ComponentScan(basePackageClasses = CustomBannerProvider.class)
public class CmdAppConfig {

    /**
     * @return Console command handler
     */
    @Bean
    public ConsoleCommands consoleCommands() {

        return new ConsoleCommands();
    }

    /**
     * @return Exit command handler
     */
    @Bean
    public ExitCommands exitCommands() {

        return new ExitCommands();
    }

    /**
     * @return Help command handler
     */
    @Bean
    public HelpCommands helpCommands() {

        return new HelpCommands();
    }

    /**
     * @return Inline command command (like ';')
     */
    @Bean
    public InlineCommentCommands inlineCommentCommands() {

        return new InlineCommentCommands();
    }

    /**
     * @return Script command handler
     */
    @Bean
    public ScriptCommands scriptCommands() {

        return new ScriptCommands();
    }

    /**
     * @return Command line parser
     */
    @Bean
    public CmdLineParser parser() {

        return new CmdLineParser();
    }
}
