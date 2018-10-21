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

import com.github.chrisblutz.jetway.aixm.AIXMConverter;
import org.apache.xmlbeans.XmlAnySimpleType;

/**
 * Represents a default converter for generic AIXM types that
 * can be converted into {@link String} instances.  These are
 * then passed to a {@link StringConverter} for final conversion.
 *
 * @param <T> the type to convert to
 */
public class DefaultAIXMConverter<T> extends AIXMConverter<T> {

    private static final Class<?>[] DEFAULT_ACCEPTED_TYPES = new Class<?>[]{XmlAnySimpleType.class, String.class};

    private T defaultValue;
    private StringConverter<T> converter;

    /**
     * Creates a new {@code DefaultAIXMConverter} with the specified
     * default value and the specified converter to convert from a
     * {@link String} to the final type.
     *
     * @param defaultValue the default value
     * @param converter    the final converter from {@link String} to {@code T}
     */
    public DefaultAIXMConverter(T defaultValue, StringConverter<T> converter) {

        this.defaultValue = defaultValue;
        this.converter = converter;
    }

    /**
     * Converts the specified AIXM object into an object of the type {@code T},
     * using this converter's {@link StringConverter}.
     *
     * @param value the AIXM (or native Java) object to convert.
     *              This object is guaranteed to be either {@code null}
     *              or an instance of one of the classes specified by
     *              {@link AIXMConverter#acceptedTypes()}.
     * @return The converted value
     */
    @Override
    public T convert(Object value) {

        String string = getDefaultTypeAsString(value);
        if (string == null) {

            return defaultValue;

        } else {

            return converter.convert(string);
        }
    }

    /**
     * Retrieves the types accepted by this converter as an array.
     *
     * @return A {@code Class[]} of types accepted by this converter
     */
    @Override
    public Class<?>[] acceptedTypes() {

        return DEFAULT_ACCEPTED_TYPES;
    }

    private static String getDefaultTypeAsString(Object value) {

        if (value instanceof XmlAnySimpleType) {

            return ((XmlAnySimpleType) value).getStringValue();

        } else if (value instanceof String) {

            return (String) value;

        } else {

            return null;
        }
    }
}
