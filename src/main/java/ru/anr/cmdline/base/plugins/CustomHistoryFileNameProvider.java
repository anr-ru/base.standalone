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

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.HistoryFileNameProvider;
import org.springframework.stereotype.Component;

/**
 * Privider for history file name.
 * 
 * 
 * @author Alexey Romanchuk
 * @created Oct 28, 2014
 * 
 */
@Component("history")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomHistoryFileNameProvider implements HistoryFileNameProvider {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHistoryFileName() {

        return "shell-cmd-history.log";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProviderName() {

        return "history";
    }
}
