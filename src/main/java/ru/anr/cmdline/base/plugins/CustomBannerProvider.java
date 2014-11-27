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
package ru.anr.cmdline.base.plugins;

import java.io.IOException;

import jline.internal.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.shell.plugin.support.DefaultBannerProvider;
import org.springframework.shell.support.util.FileUtils;
import org.springframework.shell.support.util.OsUtils;
import org.springframework.stereotype.Component;

/**
 * Banner Provider implementation.
 * 
 * 
 * @author Alexey Romanchuk
 * @created Oct 28, 2014
 * 
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomBannerProvider extends DefaultBannerProvider {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(CustomBannerProvider.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBanner() {

        StringBuilder sb = new StringBuilder();

        try {

            InputStreamSource iss = new ClassPathResource("welcome.text.txt");
            sb.append(FileUtils.readBanner(new InputStreamReader(iss.getInputStream())));

        } catch (IOException ex) {
            logger.info("File 'welcome.text.txt' not found in classpath");
        }

        sb.append(OsUtils.LINE_SEPARATOR);
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProviderName() {

        return "App";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getWelcomeMessage() {

        return "Welcome to application. For command reference type \"help\" then hit ENTER";
    }
}
