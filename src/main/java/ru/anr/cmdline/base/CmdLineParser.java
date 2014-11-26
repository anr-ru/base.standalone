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
package ru.anr.cmdline.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.shell.core.ExitShellRequest;

import ru.anr.base.services.BaseServiceImpl;

/**
 * An application specific handler, which starts a command line shell.
 * 
 * @author Alexey Romanchuk
 * @created Oct 28, 2014
 * 
 */
public class CmdLineParser extends BaseServiceImpl implements CommandLineRunner {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(CmdLineParser.class);

    /**
     * {@link Bootstrap} bean
     */
    @Autowired
    @Qualifier("bootstrap")
    private Bootstrap bootstrap;

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(String... args) throws Exception {

        logger.debug("Initial command line args: {}", list(args));

        ExitShellRequest exitShellRequest = bootstrap.run();
        logger.info("Shell exit code: {}", exitShellRequest.getExitCode());
    }
}
