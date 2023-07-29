/*
 * Copyright 2014-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package ru.anr.base.cmdline.custom;

import org.jline.terminal.Terminal;

/**
 * A progress counter implementation.
 * <p>
 * The idea was used from: <a href="https://github.com/dmadunic/clidemo">...</a>
 *
 * @created Mar 21, 2021
 */
public class ProgressCounter {
    private static final String CUU = "\u001B[A";

    private final Terminal terminal;
    private char[] spinner = {'|', '/', '-', '\\'};

    private String pattern = " %s: %d ";

    private boolean started = false;
    private int spinCounter = 0;

    public ProgressCounter(Terminal terminal) {
        this(terminal, null);
    }

    public ProgressCounter(Terminal terminal, String pattern) {
        this(terminal, pattern, null);
    }

    public ProgressCounter(Terminal terminal, String pattern, char[] spinner) {
        this.terminal = terminal;

        if (pattern != null) {
            this.pattern = pattern;
        }
        if (spinner != null) {
            this.spinner = spinner;
        }
    }

    public void display(int count, String message) {
        if (!started) {
            terminal.writer().println();
            started = true;
        }
        String progress = String.format(pattern, message, count);

        terminal.writer().println(CUU + "\r" + getSpinnerChar() + progress);
        terminal.flush();
    }

    public void display() {
        if (!started) {
            terminal.writer().println();
            started = true;
        }
        terminal.writer().println(CUU + "\r" + getSpinnerChar());
        terminal.flush();
    }

    public void reset() {
        spinCounter = 0;
        started = false;
    }

    private char getSpinnerChar() {
        char spinChar = spinner[spinCounter];
        spinCounter++;
        if (spinCounter == spinner.length) {
            spinCounter = 0;
        }
        return spinChar;
    }

    public char[] getSpinner() {
        return spinner;
    }

    public void setSpinner(char[] spinner) {
        this.spinner = spinner;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
