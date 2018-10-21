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

package com.github.chrisblutz.jetway.aixm;

/**
 * This class contains methods to convert raw AIXM information
 * into Jetway's types.
 *
 * @param <T> the Jetway (or native Java) type produced
 *            by this converter
 * @author Christopher Lutz
 */
public abstract class AIXMConverter<T> {

    /**
     * Converts a raw AIXM object (or native Java object) into
     * this converter's Jetway (or native Java) type.
     *
     * @param value the AIXM (or native Java) object to convert.
     *              This object is guaranteed to be either {@code null}
     *              or an instance of one of the classes specified by
     *              {@link AIXMConverter#acceptedTypes()}.
     * @return The converted object.  If the input object was {@code null},
     * this should be the default value for the resulting type.
     */
    public abstract T convert(Object value);

    /**
     * Defines which types are accepted by the {@link AIXMConverter#convert(Object)}
     * method.  Objects are permitted to be one of the provided classes or a subclass
     * of one of the provided classes.
     *
     * @return A {@code Class[]} containing all accepted types
     */
    public abstract Class<?>[] acceptedTypes();
}
