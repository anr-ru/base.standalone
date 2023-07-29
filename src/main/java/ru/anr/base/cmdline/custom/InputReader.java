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

import org.jline.reader.LineReader;
import org.springframework.util.StringUtils;
import ru.anr.base.BaseParent;

import java.util.*;

/**
 * A customized input reader for implementing several typical interaction mode:
 * 1. A masked passwords
 * 2. Selection from a fixed list
 * 3. A choice from two values (like 'yes' | 'no' ).
 * <p>
 * The idea was used from: <a href="https://github.com/dmadunic/clidemo">...</a>
 *
 * @created Mar 21, 2021
 */
public class InputReader extends BaseParent {

    public static final Character DEFAULT_MASK = '*';

    private final Character mask;

    private final LineReader lineReader;

    private final ShellService shellService;

    public InputReader(LineReader lineReader, ShellService shellService) {
        this(lineReader, shellService, null);
    }

    public InputReader(LineReader lineReader, ShellService shellService, Character mask) {
        this.lineReader = lineReader;
        this.shellService = shellService;
        this.mask = mask != null ? mask : DEFAULT_MASK;
    }

    public String prompt(String prompt) {
        return prompt(prompt, null, true);
    }

    public String prompt(String prompt, String defaultValue) {
        return prompt(prompt, defaultValue, true);
    }

    /**
     * Prompts for input
     *
     * @param prompt       The prompt message
     * @param defaultValue The default value if nothing entered
     * @param echo         true, if the input value must be display (or false if masked).
     */
    public String prompt(String prompt, String defaultValue, boolean echo) {

        String answer = "";
        if (echo) {
            answer = lineReader.readLine(prompt + ": ");
        } else {
            answer = lineReader.readLine(prompt + ": ", mask);
        }
        if (isEmpty(answer)) {
            return defaultValue;
        }
        return answer;
    }

    /**
     * Prompt for input from the predefined options values.
     *
     * @param prompt        The prompt message
     * @param defaultValue  The default value
     * @param optionsAsList The list of options
     * @return The chosen value
     */
    public String promptWithOptions(String prompt, String defaultValue, List<String> optionsAsList) {
        String answer;

        List<String> allowedAnswers = new ArrayList<>(optionsAsList);
        if (StringUtils.hasText(defaultValue)) {
            allowedAnswers.add("");
        }
        do {
            answer = lineReader.readLine(String.format("%s %s: ", prompt, formatOptions(defaultValue, optionsAsList)));
        } while (!allowedAnswers.contains(answer) && !"".equals(answer));

        if (isEmpty(answer) && allowedAnswers.contains("")) {
            return defaultValue;
        }
        return answer;
    }

    private List<String> formatOptions(String defaultValue, List<String> optionsAsList) {
        List<String> result = new ArrayList<>();
        for (String option : optionsAsList) {
            String val = option;
            if ("".equals(option) || option == null) {
                val = "''";
            }
            if (defaultValue != null) {
                if (defaultValue.equals(option) || (defaultValue.equals("") && option == null)) {
                    val = shellService.getInfoMessage(val);
                }
            }
            result.add(val);
        }
        return result;
    }

    /**
     * Prompts from the list with given descriptions of values.
     *
     * @param headingMessage The start message
     * @param promptMessage  The prompt message
     * @param options        The map of value/description
     * @param ignoreCase     true, if need to ignore the case
     * @param defaultValue   The default value to use
     * @return The selected value
     */
    public String selectFromList(String headingMessage, String promptMessage, Map<String, String> options,
                                 boolean ignoreCase, String defaultValue) {
        String answer;
        Set<String> allowedAnswers = new HashSet<>(options.keySet());
        if (defaultValue != null && !defaultValue.equals("")) {
            allowedAnswers.add("");
        }

        shellService.print(String.format("%s: ", headingMessage));
        do {
            for (Map.Entry<String, String> option : options.entrySet()) {
                String defaultMarker = null;
                if (defaultValue != null) {
                    if (option.getKey().equals(defaultValue)) {
                        defaultMarker = "*";
                    }
                }
                if (defaultMarker != null) {
                    shellService.info(String.format("%s [%s] %s ", defaultMarker, option.getKey(), option.getValue()));
                } else {
                    shellService.print(String.format("  [%s] %s", option.getKey(), option.getValue()));
                }
            }
            answer = lineReader.readLine(String.format("%s: ", promptMessage));
        } while (!containsString(allowedAnswers, answer, ignoreCase) && !"".equals(answer));

        if (isEmpty(answer) && allowedAnswers.contains("")) {
            return defaultValue;
        }
        return answer;
    }

    private boolean containsString(Set<String> l, String s, boolean ignoreCase) {
        if (!ignoreCase) {
            return l.contains(s);
        }
        for (String value : l) {
            if (value.equalsIgnoreCase(s))
                return true;
        }
        return false;
    }
}
