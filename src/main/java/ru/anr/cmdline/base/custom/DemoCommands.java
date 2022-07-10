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
package ru.anr.cmdline.base.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * A set of basic demo commands
 *
 * @author Alexey Romanchuk
 * @created Oct 28, 2014
 */
@ShellComponent
public class DemoCommands extends BaseCommand {

    @Autowired
    protected InputReader inputReader;

    ///////////////////////////////////////////////////////////////////////////
    ///////// Demonstration for outputs

    @ShellMethod("Displays a message with all predefined colors")
    public String echo(@ShellOption({"-T", "--text"}) String text) {

        String msg = String.format("Hello %s!", text);

        shellService.print(msg.concat(" (Default style message)"));
        shellService.error(msg.concat(" (Error style message)"));
        shellService.warn(msg.concat(" (Warning style message)"));
        shellService.info(msg.concat(" (Info style message)"));
        shellService.success(msg.concat(" (Success style message)"));

        String output = shellService.getSuccessMessage(msg);
        return output.concat(" Resulted as a successful message.");
    }

    @ShellMethod("Displays progress spinner")
    public void spinner() throws InterruptedException {
        for (int i = 1; i <= 100; i++) {
            progressCounter.display();
            Thread.sleep(100);
        }
        progressCounter.reset();
    }

    @ShellMethod("Displays progress counter (with spinner)")
    public void counter() throws InterruptedException {
        for (int i = 1; i <= 100; i++) {
            progressCounter.display(i, "Processing");
            Thread.sleep(100);
        }
        progressCounter.reset();
    }

    @ShellMethod("Displays progress bar")
    public void progress() throws InterruptedException {
        for (int i = 1; i <= 100; i++) {
            progressBar.display(i);
            Thread.sleep(100);
        }
        progressBar.reset();
    }

    @ShellMethod("Interactive input demonstration")
    public void interact() {

        // 1. Ask for a password
        do {
            String pwd = inputReader.prompt("Password", "secret", false);
            if (StringUtils.hasText(pwd)) {
                shellService.info("Password is OK");
                break;
            } else {
                shellService.warn("Password cannot be empty");
            }
        } while (true);

        // 2. Play with a selection from lists
        Map<String, String> options = new HashMap<>();
        options.put("M", "Male");
        options.put("F", "Female");

        String genderValue = inputReader.selectFromList("Gender",
                "Please enter one of the [] values", options, true, null);
        shellService.info("You entered: " + genderValue.toUpperCase());

        // 3. Ask for 'Y' or 'N' options
        String value = inputReader.promptWithOptions("Are you sure?", "N", list("Y", "N"));
        shellService.info("You chose: " + value);

        shellService.success("---> PROCEDURE COMPLETED");
    }

}
