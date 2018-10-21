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

import com.github.chrisblutz.jetway.aixm.AIXMAssignable;
import com.github.chrisblutz.jetway.aixm.AIXMFeature;
import com.github.chrisblutz.jetway.aixm.AIXMType;
import com.github.chrisblutz.jetway.caching.CacheEntry;
import com.github.chrisblutz.jetway.caching.Cacheable;
import com.github.chrisblutz.jetway.caching.features.RunwayCacheUtils;

import java.util.UUID;

/**
 * Represents a runway at an airport.
 *
 * @author Christopher Lutz
 */
public class Runway implements AIXMAssignable<RunwayEnd>, Cacheable {

    private String designator;
    private double length, width;

    private RunwayEnd baseEnd = null, reciprocalEnd = null;

    /**
     * Loads a {@code Runway} instance based on the specified {@link CacheEntry}.
     *
     * @param entry the entry being loaded
     * @return The resulting {@code Runway} instance
     */
    public static Runway loadRunwayFromCache(CacheEntry entry) {

        Runway runway = new Runway();
        runway.loadFromCache(entry);
        return runway;
    }

    /**
     * Retrieves the designator for this runway.
     *
     * @return The designator for this runway
     */
    public String getDesignator() {

        return designator;
    }

    /**
     * Retrieves the length of this runway in feet.
     *
     * @return The length of this runway in feet
     */
    public double getLength() {

        return length;
    }

    /**
     * Retrieves the width of this runway in feet.
     *
     * @return The length of this runway in feet
     */
    public double getWidth() {

        return width;
    }

    /**
     * Assigns a loaded {@link RunwayEnd}to this airport.
     *
     * @param uuid  the {@link UUID} key
     * @param value the {@link RunwayEnd} object
     */
    @Override
    public void assign(UUID uuid, RunwayEnd value) {

        if (baseEnd == null) {

            baseEnd = value;

        } else {

            reciprocalEnd = value;
        }
    }

    /**
     * Loads data from an {@link AIXMFeature} into this {@code Runway} object.
     *
     * @param type    the type of feature currently being loaded.  This is
     *                useful if multiple feature types are loaded into the
     *                same class.
     * @param feature the feature data
     */
    @Override
    public void loadFromAIXM(AIXMType type, AIXMFeature feature) {

        designator = feature.getString("Designator");
        length = feature.getDouble("LengthStrip");
        width = feature.getDouble("WidthStrip");
    }

    /**
     * Loads data from a {@link CacheEntry} into this {@code Runway} object.
     *
     * @param entry the cache entry being loaded
     */
    @Override
    public void loadFromCache(CacheEntry entry) {

        designator = entry.get(RunwayCacheUtils.DESIGNATOR);
        length = entry.getDouble(RunwayCacheUtils.LENGTH);
        width = entry.getDouble(RunwayCacheUtils.WIDTH);

        boolean hasBase = entry.getBoolean(RunwayCacheUtils.HAS_BASE_END);
        if (hasBase) {

            baseEnd = RunwayEnd.loadRunwayEndFromCache(RunwayCacheUtils.BASE_END_PREFIX, entry);
        }

        boolean hasReciprocal = entry.getBoolean(RunwayCacheUtils.HAS_RECIPROCAL_END);
        if (hasReciprocal) {

            reciprocalEnd = RunwayEnd.loadRunwayEndFromCache(RunwayCacheUtils.RECIPROCAL_END_PREFIX, entry);
        }
    }

    /**
     * Saves this {@code Runway} object into a {@link CacheEntry}.
     *
     * @param entry the entry being saved to
     */
    @Override
    public void saveToCache(CacheEntry entry) {

        entry.put(RunwayCacheUtils.DESIGNATOR, getDesignator());
        entry.put(RunwayCacheUtils.LENGTH, getLength());
        entry.put(RunwayCacheUtils.WIDTH, getWidth());

        boolean hasBase = baseEnd != null;
        entry.put(RunwayCacheUtils.HAS_BASE_END, hasBase);
        if (hasBase) {

            baseEnd.saveToCache(RunwayCacheUtils.BASE_END_PREFIX, entry);
        }

        boolean hasReciprocal = reciprocalEnd != null;
        entry.put(RunwayCacheUtils.HAS_RECIPROCAL_END, hasReciprocal);
        if (hasReciprocal) {

            reciprocalEnd.saveToCache(RunwayCacheUtils.RECIPROCAL_END_PREFIX, entry);
        }
    }
}
