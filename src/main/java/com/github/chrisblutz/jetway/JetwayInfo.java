/*
 * Copyright 2018 Christopher Lutz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.chrisblutz.jetway;

import org.apache.logging.log4j.Logger;

final class JetwayInfo {

    private static String version = null;

    private JetwayInfo() {

    }

    static String getVersion() {

        if (version == null) {

            Package p = Jetway.class.getPackage();
            if (p == null) {

                version = "Unknown (Development Environment)";

            } else {

                version = p.getImplementationTitle() + " v" + p.getImplementationVersion();
            }
        }

        return version;
    }

    static void logInformation(Logger logger) {

        logger.info("Jetway Version:  " + getVersion());
        logger.info("Java Version:    " + System.getProperty("java.version"));
        logger.info("Java Vendor:     " + System.getProperty("java.vendor"));
        logger.info("OS Name:         " + System.getProperty("os.name"));
        logger.info("OS Version:      " + System.getProperty("os.version"));
        logger.info("OS Architecture: " + System.getProperty("os.arch"));

        long memoryLimit = Runtime.getRuntime().maxMemory();
        logger.info("VM Memory Limit: " + (memoryLimit == Long.MAX_VALUE ? "None" : formatMemoryAsString(memoryLimit)));
    }

    private static String formatMemoryAsString(long bytes) {

        String[] suffixes = new String[]{"bytes", "KB", "MB", "GB", "TB", "PB"};

        double bytesDouble = bytes;
        int level;
        for (level = 0; level < suffixes.length; level++) {

            if (bytesDouble < 1024) {

                break;
            }

            bytesDouble /= 1024;
        }

        return String.format("%.3f " + suffixes[level], bytesDouble);
    }
}
