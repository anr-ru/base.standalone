/*
 * Copyright 2014-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package ru.anr.base.cmdline.custom;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Value;
import ru.anr.base.BaseParent;

/**
 * A helper service for managing colored output.
 * The idea was used from: <a href="https://github.com/dmadunic/clidemo">...</a>
 *
 * @created Mar 21, 2021
 */
public class ShellService {

    public enum PromptColor {
        BLACK(0),
        RED(1),
        GREEN(2),
        YELLOW(3),
        BLUE(4),
        MAGENTA(5),
        CYAN(6),
        WHITE(7),
        BRIGHT(8);

        private final int value;

        PromptColor(int value) {
            this.value = value;
        }

        public int toJlineAttributedStyle() {
            return this.value;
        }

        public static PromptColor findValue(String str) {
            int v = Integer.parseInt(str);
            return BaseParent.list(values()).stream().filter(c -> c.value == v)
                    .findFirst()
                    .orElseGet(() -> BLUE);
        }
    }

    @Value("${shell.colors.info:6}")
    public String infoColor;

    @Value("${shell.colors.success:2}")
    public String successColor;

    @Value("${shell.colors.warning:3}")
    public String warningColor;

    @Value("${shell.colors.error:1}")
    public String errorColor;

    private final Terminal terminal;

    public ShellService(Terminal terminal) {
        this.terminal = terminal;
    }

    public String getColored(String message, PromptColor color) {
        return (new AttributedStringBuilder()).append(message,
                AttributedStyle.DEFAULT.foreground(color.toJlineAttributedStyle())).toAnsi();
    }

    public String getInfoMessage(String message) {
        return getColored(message, PromptColor.findValue(infoColor));
    }

    public String getSuccessMessage(String message) {
        return getColored(message, PromptColor.findValue(successColor));
    }

    public String getWarningMessage(String message) {
        return getColored(message, PromptColor.findValue(warningColor));
    }

    public String getErrorMessage(String message) {
        return getColored(message, PromptColor.findValue(errorColor));
    }

    public void print(String message) {
        print(message, null);
    }

    public void success(String message) {
        print(message, PromptColor.findValue(successColor));
    }

    public void info(String message) {
        print(message, PromptColor.findValue(infoColor));
    }

    public void warn(String message) {
        print(message, PromptColor.findValue(warningColor));
    }

    public void error(String message) {
        print(message, PromptColor.findValue(errorColor));
    }

    public void print(String message, PromptColor color) {
        String toPrint = message;
        if (color != null) {
            toPrint = getColored(message, color);
        }
        terminal.writer().println(toPrint);
        terminal.flush();
    }

    public Terminal getTerminal() {
        return terminal;
    }
}
