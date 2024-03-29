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

import java.util.UUID;

/**
 * This interface defines an AIXM feature that is loadable
 * (see {@link AIXMLoadable}) and can have other AIXM features
 * assigned to it (of type {@code T}).
 *
 * @param <T> the type of {@link AIXMLoadable} that should be accepted
 * @author Christopher Lutz
 */
public interface AIXMAssignable<T extends AIXMLoadable> extends AIXMLoadable {

    /**
     * Assigns another AIXM feature to this feature.
     *
     * @param uuid  the {@link UUID} key
     * @param value the AIXM feature object
     */
    void assign(UUID uuid, T value);
}
