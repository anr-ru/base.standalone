/*
 * Copyright 2014 the original author or authors.
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
package ru.anr.cmdline.utils;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import jline.internal.InputStreamReader;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.support.util.OsUtils;

/**
 * A simple wrapper for os command execution.
 * 
 * 
 * @author Alexey Romanchuk
 * @created Oct 28, 2014
 * 
 */

public class SimpleOsCommand {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(SimpleOsCommand.class);

    /**
     * Execute specified command
     * 
     * @param command
     *            An os command
     * @return Command result (stdout or stderr)
     * @throws IOException
     *             When error with result processing occures
     */
    public String execute(String command) throws IOException {

        StringBuilder sb = new StringBuilder();
        final File root = new File("."); // Starting from a current directory

        try {

            final Process p = Runtime.getRuntime().exec(command, null, root);

            Reader input = new InputStreamReader(p.getInputStream());
            Reader errors = new InputStreamReader(p.getErrorStream());

            for (String s : IOUtils.readLines(input)) { // stdout
                sb.append(s);
                sb.append(OsUtils.LINE_SEPARATOR);
            }

            for (String s : IOUtils.readLines(errors)) { // stderr
                sb.append(s);
                sb.append(OsUtils.LINE_SEPARATOR);
            }

            p.getOutputStream().close();

            if (p.waitFor() != 0) {
                logger.error("The command '{}' did not complete successfully", command);
            }
        } catch (final InterruptedException e) {
            throw new IllegalStateException(e);
        }
        return sb.toString();
    }
}
