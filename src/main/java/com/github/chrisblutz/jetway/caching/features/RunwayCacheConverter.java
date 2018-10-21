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
import com.github.chrisblutz.jetway.features.Runway;
import com.github.chrisblutz.jetway.features.Runways;

import java.util.UUID;

/**
 * Converts {@link Runway} instances into cache information and vice versa.
 */
public class RunwayCacheConverter extends CacheConverter<UUID, Runway> {

    /**
     * Loads a {@link Runway} instance from a {@link CacheEntry}.
     *
     * @param entry the entry to load from
     * @return The {@link Runway} instance
     */
    @Override
    public Runway load(CacheEntry entry) {

        return Runway.loadRunwayFromCache(entry);
    }

    /**
     * Saves a {@link Runway} instance to a {@link CacheEntry}.
     *
     * @param runway the {@link Runway} to save
     * @param entry  the entry to save to
     */
    @Override
    public void save(Runway runway, CacheEntry entry) {

        runway.saveToCache(entry);
    }

    /**
     * Loads a {@link UUID} key for an {@link Runway}.
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
     * Loads persistent data for a {@link Runway} instance
     * from a {@link String}.
     *
     * @param key  the key for the {@link Runway}
     * @param data the persistent data as a {@link String}
     */
    @Override
    public void loadPersistentData(UUID key, String data) {

        Runways.registerDesignator(key, data);
    }

    /**
     * Saves the persistent data for a {@link Runway} instance
     * to a {@link String}.
     *
     * @param runway the {@link Runway} instance
     * @return The persistent data as a {@link String}
     */
    @Override
    public String savePersistentData(Runway runway) {

        return runway.getDesignator();
    }
}
