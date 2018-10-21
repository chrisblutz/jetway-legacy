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

package com.github.chrisblutz.jetway.aixm.converters;

/**
 * Represents a converter from {@link String} instances
 * to a final type ({@code T}).  Instances of this class
 * are used by {@link DefaultAIXMConverter} to convert
 * AIXM values.
 *
 * @param <T> the type to convert to
 */
public interface StringConverter<T> {

    /**
     * Converts {@link String} instances into objects
     * of type {@code T}.
     *
     * @param string the {@link String} to convert
     * @return The converted value
     */
    T convert(String string);
}
