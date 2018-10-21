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
 * This is a utility class for Jetway's runway cache.
 *
 * @author Christopher Lutz
 */
public class RunwayCacheUtils {

    /**
     * The key for runway designators
     */
    public static final String DESIGNATOR = "designator";
    /**
     * The key for runway lengths
     */
    public static final String LENGTH = "length";
    /**
     * The key for runway widths
     */
    public static final String WIDTH = "width";
    /**
     * The key for runway base end presence
     */
    public static final String HAS_BASE_END = "hasBaseEnd";
    /**
     * The key for runway reciprocal end presence
     */
    public static final String HAS_RECIPROCAL_END = "hasReciprocalEnd";

    /**
     * The base end cache entry prefix
     */
    public static final String BASE_END_PREFIX = "base_";
    /**
     * The reciprocal end cache entry prefix
     */
    public static final String RECIPROCAL_END_PREFIX = "reciprocal_";

    /**
     * The key for runway end designators
     */
    public static final String END_DESIGNATOR = "endDesignator";

    private static CacheConverter<UUID, Runway> runwayConverter = null;

    /**
     * Retrieves the {@link CacheConverter} for {@link Runway} instances.
     *
     * @return The runway cache converter
     */
    public static CacheConverter<UUID, Runway> getRunwayConverter() {

        if (runwayConverter == null) {

            runwayConverter = new CacheConverter<UUID, Runway>() {

                @Override
                public Runway load(CacheEntry entry) {

                    return Runway.loadRunwayFromCache(entry);
                }

                @Override
                public void save(Runway runway, CacheEntry entry) {

                    runway.saveToCache(entry);
                }

                @Override
                public UUID loadKey(String str) {

                    return UUID.fromString(str);
                }

                @Override
                public String saveKey(UUID key) {

                    return key.toString();
                }

                @Override
                public void loadPersistentData(UUID key, String data) {

                    Runways.registerDesignator(key, data);
                }

                @Override
                public String savePersistentData(Runway runway) {

                    return runway.getDesignator();
                }
            };
        }

        return runwayConverter;
    }
}
