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

package com.github.chrisblutz.jetway.exceptions;

/**
 * The main exception class for Jetway, which might
 * be used to wrap another exception.
 *
 * @author Christopher Lutz
 */
public class JetwayException extends Exception {

    /**
     * Creates a new {@code JetwayException} with the
     * specified message.
     *
     * @param message the message
     */
    public JetwayException(String message) {

        super(message);
    }

    /**
     * Creates a new {@code JetwayException} with the
     * specified message and cause.
     *
     * @param message the message
     * @param cause   the cause
     */
    public JetwayException(String message, Throwable cause) {

        super(message, cause);
    }
}
