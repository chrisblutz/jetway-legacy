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
 * Represents an AIXM extension to an AIXM feature.
 *
 * @author Christopher Lutz
 */
public class AIXMFeatureExtension extends AIXMData {

    /**
     * Creates a new feature extension with the specified
     * underlying data.
     *
     * @param data the underlying data for this extension
     */
    public AIXMFeatureExtension(Object data) {

        super(data);
    }
}
