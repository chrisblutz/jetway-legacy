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

package com.github.chrisblutz.jetway.caching.features;

import com.github.chrisblutz.jetway.caching.CacheConverter;
import com.github.chrisblutz.jetway.caching.CacheEntry;
import com.github.chrisblutz.jetway.features.Airport;
import com.github.chrisblutz.jetway.features.Airports;

import java.util.UUID;

/**
 * Converts {@link Airport} instances into cache information and vice versa.
 */
class AirportCacheConverter extends CacheConverter<UUID, Airport> {

    /**
     * Loads an {@link Airport} instance from a {@link CacheEntry}.
     *
     * @param entry the entry to load from
     * @return The {@link Airport} instance
     */
    @Override
    public Airport load(CacheEntry entry) {

        return Airport.loadAirportFromCache(entry);
    }

    /**
     * Saves an {@link Airport} instance to a {@link CacheEntry}.
     *
     * @param airport the {@link Airport} to save
     * @param entry   the entry to save to
     */
    @Override
    public void save(Airport airport, CacheEntry entry) {

        airport.saveToCache(entry);
    }

    /**
     * Loads a {@link UUID} key for an {@link Airport}.
     *
     * @param str the string to load from
     * @return The {@link UUID} key
     */
    @Override
    public UUID loadKey(String str) {

        return UUID.fromString(str);
    }

    /**
     * Saves a {@link UUID} key to a {@link String}.
     *
     * @param key the {@link UUID} key to save
     * @return The resulting {@link String}
     */
    @Override
    public String saveKey(UUID key) {

        return key.toString();
    }

    /**
     * Loads persistent data for an {@link Airport} instance
     * from a {@link String}.
     *
     * @param key  the key for the {@link Airport}
     * @param data the persistent data as a {@link String}
     */
    @Override
    public void loadPersistentData(UUID key, String data) {

        String[] parts = data.split("=", 2);
        String[] lengths = parts[0].split(",");
        data = parts[1];

        int nameLength = Integer.parseInt(lengths[0]);
        String name = data.substring(0, nameLength);
        data = data.substring(nameLength);

        int designatorLength = Integer.parseInt(lengths[1]);
        String designator = data.substring(0, designatorLength);
        data = data.substring(designatorLength);

        int identifierLength = Integer.parseInt(lengths[2]);
        String identifier = data.substring(0, identifierLength);

        Airports.register(key, name, designator, identifier);
    }

    /**
     * Saves the persistent data for an {@link Airport} instance
     * to a {@link String}.
     *
     * @param airport the {@link Airport} instance
     * @return The persistent data as a {@link String}
     */
    @Override
    public String savePersistentData(Airport airport) {

        String name = airport.getName();
        String designator = airport.getIATADesignator();
        String identifier = airport.getICAOIdentifier();
        int nameLength = name.length();
        int designatorLength = designator.length();
        int identifierLength = identifier == null ? 0 : identifier.length();

        return nameLength + "," + designatorLength + "," + identifierLength + "=" + name + designator + (identifier == null ? "" : identifier);
    }
}
