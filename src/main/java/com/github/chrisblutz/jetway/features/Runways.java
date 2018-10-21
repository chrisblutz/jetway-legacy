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

package com.github.chrisblutz.jetway.features;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This class is the container for persistent runway data.
 *
 * @author Christopher Lutz
 */
public final class Runways {

    private static Map<UUID, String> designatorMappings = new HashMap<>();

    private Runways() {

    }

    /**
     * Registers a runway identifier for a {@link UUID} key.
     *
     * @param key        the {@link UUID} key for the {@link Runway}
     * @param designator the runway designator
     */
    public static void registerDesignator(UUID key, String designator) {

        designatorMappings.put(key, designator);
    }

    /**
     * Retrieves the designator for the {@link Runway} with the
     * specified {@link UUID} key.
     *
     * @param uuid the {@link UUID} key of the runway
     * @return The designator of the indicated runway
     */
    public static String getDesignatorForUUID(UUID uuid) {

        return designatorMappings.get(uuid);
    }
}
