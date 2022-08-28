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

/**
 * A progress bar implementation.
 * <p>
 * The idea was used from: <a href="https://github.com/dmadunic/clidemo">...</a>
 *
 * @created Mar 21, 2021
 */
public class ProgressBar {
    private static final String CUU = "\u001B[A";
    private static final String DL = "\u001B[1M";

    private String doneMarker = "=";
    private String remainsMarker = "-";
    private String leftDelimiter = "";
    private String rightDelimiter = "";

    ShellService shellService;

    private boolean started = false;

    public ProgressBar(ShellService shellService) {
        this.shellService = shellService;
    }

    public void display(int percentage) {
        display(percentage, null);
    }

    public void display(int percentage, String statusMessage) {
        if (!started) {
            started = true;
            shellService.getTerminal().writer().println();
        }

        int x = (percentage / 5);
        int y = 20 - x;
        String message = ((statusMessage == null) ? "" : statusMessage);

        String done = shellService.getSuccessMessage(new String(new char[x]).replace("\0", doneMarker));
        String remains = new String(new char[y]).replace("\0", remainsMarker);

        String progressBar = String.format("%s%s%s%s %d", leftDelimiter, done, remains, rightDelimiter, percentage);

        shellService.getTerminal().writer().println(CUU + "\r" + DL + progressBar + "% " + message);
        shellService.getTerminal().flush();
    }

    public void reset() {
        started = false;
    }

    public String getDoneMarker() {
        return doneMarker;
    }

    public void setDoneMarker(String doneMarker) {
        this.doneMarker = doneMarker;
    }

    public String getRemainsMarker() {
        return remainsMarker;
    }

    public void setRemainsMarker(String remainsMarker) {
        this.remainsMarker = remainsMarker;
    }

    public String getLeftDelimiter() {
        return leftDelimiter;
    }

    public void setLeftDelimiter(String leftDelimiter) {
        this.leftDelimiter = leftDelimiter;
    }

    public String getRightDelimiter() {
        return rightDelimiter;
    }

    public void setRightDelimiter(String rightDelimiter) {
        this.rightDelimiter = rightDelimiter;
    }
}
