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

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a type of AIXM feature data present in the NASR
 * files.
 * <p>
 * These are based on {@link String} identifiers, which should be
 * formatted as follows:
 * <pre>
 * FeatureName[id=ID_PREFIX]
 * </pre>
 * The {@code [id=ID_PREFIX]} is optional, and is used to distinguish
 * between multiple types of features using the same feature
 * identifier (i.e. runways and runway ends).
 *
 * @author Christopher Lutz
 */
public class AIXMType {

    private static Map<String, AIXMType> aixmTypeMap = new HashMap<>();

    private String identifier;
    private Class<? extends AIXMAssignable> assignTo;

    private AIXMType(String identifier, Class<? extends AIXMAssignable> assignTo) {

        this.identifier = identifier;
        this.assignTo = assignTo;
    }

    /**
     * Retrieves the type for the specified identifier and no assignment
     * class.
     * <p>
     * This method is equivalent to calling
     * <pre>
     *     forIdentifier(identifier, null);
     * </pre>
     *
     * @param identifier the identifier of the type (formatted as {@code FeatureName[id=ID_PREFIX]})
     * @return The {@code AIXMType} corresponding to the identifier
     * @see AIXMType#forIdentifier(String, Class)
     */
    public static AIXMType forIdentifier(String identifier) {

        return forIdentifier(identifier, null);
    }

    /**
     * Retrieves the type for the specified identifier and the specified
     * assignment class.
     *
     * @param identifier the identifier of the type (formatted as {@code FeatureName[id=ID_PREFIX})
     * @param assignTo   the {@link AIXMLoadable} class that this feature should be assigned to after it is loaded
     * @return The {@code AIXMType} corresponding to the identifier
     */
    public static AIXMType forIdentifier(String identifier, Class<? extends AIXMAssignable> assignTo) {

        if (!aixmTypeMap.containsKey(identifier)) {

            aixmTypeMap.put(identifier, new AIXMType(identifier, assignTo));
        }

        return aixmTypeMap.get(identifier);
    }

    /**
     * Retrieves the identifier associated with this type.
     *
     * @return The type identifier
     */
    public String getIdentifier() {

        return identifier;
    }

    /**
     * Gets the feature class that this feature should be assigned
     * to after loading (i.e. runways get assigned to airports).
     *
     * @return The class to assign this feature to, or {@code null}
     * if this feature should not be assigned to a class.
     */
    public Class<? extends AIXMAssignable> getAssignmentClass() {

        return assignTo;
    }
}
