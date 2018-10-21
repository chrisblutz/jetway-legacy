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
 * This interface defines an AIXM feature that is loadable from
 * the raw NASR files.  Any class that implements this interface
 * <i>must</i> have a public, no-argument constructor.
 *
 * @author Christopher Lutz
 */
public interface AIXMLoadable {

    /**
     * Initializes the data in this feature from the specified type
     * and the specified feature data.
     *
     * @param type    the type of feature currently being loaded.  This is
     *                useful if multiple feature types are loaded into the
     *                same class.
     * @param feature the feature data
     */
    void loadFromAIXM(AIXMType type, AIXMFeature feature);
}
