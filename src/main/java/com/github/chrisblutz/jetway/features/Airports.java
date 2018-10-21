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

import com.github.chrisblutz.jetway.Jetway;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * This class is the container class for persistent airport data.
 *
 * @author Christopher Lutz
 */
public final class Airports {

    private static Map<String, UUID> nameMappings = new HashMap<>();
    private static Map<String, UUID> designatorMappings = new HashMap<>();
    private static Map<String, UUID> identifierMappings = new HashMap<>();

    private static Logger logger = null;

    private Airports() {

    }

    /**
     * Registers persistent data for an {@link Airport} instance.  This does
     * not indicate that the specific {@link Airport} instance is currently loaded.
     *
     * @param key        the {@link UUID} key of the airport
     * @param name       the name of the airport
     * @param designator the IATA designator for the airport
     * @param identifier the ICAO identifier for the airport
     */
    public static void register(UUID key, String name, String designator, String identifier) {

        nameMappings.put(name, key);
        designatorMappings.put(designator, key);

        if (!identifier.isEmpty()) {

            identifierMappings.put(identifier, key);
        }
    }

    /**
     * Retrieves a set of all cached airport names
     *
     * @return A set of all airport names
     */
    public static Set<String> allNames() {

        return nameMappings.keySet();
    }

    /**
     * Retrieves an {@link Airport} object for the specified name.
     *
     * @param name the name of the airport
     * @return The {@link Airport} with the specified name
     */
    public static Airport forName(String name) {

        getLogger().debug("Requesting airport for name: " + name);
        return forUUID(nameMappings.get(name));
    }

    /**
     * Retrieves a set of all cached airport IATA designators.
     *
     * @return A set of all airport designators
     */
    public static Set<String> allIATADesignators() {

        return designatorMappings.keySet();
    }

    /**
     * Retrieves an {@link Airport} object for the specified
     * International Air Transport Authority (IATA) designator.
     *
     * @param designator the IATA designator for the airport
     * @return The {@link Airport} with the specified designator
     */
    public static Airport forIATADesignator(String designator) {

        getLogger().debug("Requesting airport for IATA designator: " + designator);
        return forUUID(designatorMappings.get(designator));
    }

    /**
     * Retrieves a set of all cached airport ICAO identifiers.
     *
     * @return A set of all airport identifiers
     */
    public static Set<String> allICAOIdentifiers() {

        return identifierMappings.keySet();
    }

    /**
     * Retrieves an {@link Airport} object for the specified
     * International Civil Aviation Organization (ICAO) identifier.
     *
     * @param identifier the ICAO identifier for the airport
     * @return The {@link Airport} with the specified identifier
     */
    public static Airport forICAOIdentifier(String identifier) {

        getLogger().debug("Requesting airport for ICAO identifier: " + identifier);
        return forUUID(identifierMappings.get(identifier));
    }

    private static Airport forUUID(UUID uuid) {

        getLogger().debug("Retrieving airport for UUID " + uuid.toString());
        return Jetway.getAirportCache().get(uuid);
    }

    private static Logger getLogger() {

        if (logger == null) {

            logger = LogManager.getLogger("Feature Provider");
        }

        return logger;
    }
}
