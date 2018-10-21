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

import com.github.chrisblutz.jetway.aixm.AIXMFeature;
import com.github.chrisblutz.jetway.aixm.AIXMLoadable;
import com.github.chrisblutz.jetway.aixm.AIXMType;
import com.github.chrisblutz.jetway.caching.CacheAppendable;
import com.github.chrisblutz.jetway.caching.CacheEntry;
import com.github.chrisblutz.jetway.caching.features.RunwayCacheUtils;

/**
 * Represents a specific end of a {@link Runway}.
 *
 * @author Christopher Lutz
 */
public class RunwayEnd implements AIXMLoadable, CacheAppendable {

    private String designator;

    /**
     * Loads a {@code RunwayEnd} instance based on the specified {@link CacheEntry}.
     *
     * @param prefix the prefix for the cache entries
     * @param entry  the entry being loaded
     * @return The resulting {@code RunwayEnd} instance
     */
    public static RunwayEnd loadRunwayEndFromCache(String prefix, CacheEntry entry) {

        RunwayEnd runwayEnd = new RunwayEnd();
        runwayEnd.loadFromCache(prefix, entry);
        return runwayEnd;
    }

    /**
     * Retrieves the designator for this end of the {@link Runway}.
     *
     * @return The designator for this end of the {@link Runway}
     */
    public String getDesignator() {

        return designator;
    }

    /**
     * Loads data from an {@link AIXMFeature} into this {@code RunwayEnd}
     * object.
     *
     * @param type    the type of feature currently being loaded.  This is
     *                useful if multiple feature types are loaded into the
     *                same class.
     * @param feature the feature data
     */
    @Override
    public void loadFromAIXM(AIXMType type, AIXMFeature feature) {

        designator = feature.getString("Designator");
    }

    /**
     * Loads data from a {@link CacheEntry} into this {@code RunwayEnd} object.
     *
     * @param prefix the prefix for the cache entries
     * @param entry  the cache entry being loaded
     */
    @Override
    public void loadFromCache(String prefix, CacheEntry entry) {

        designator = entry.get(prefix + RunwayCacheUtils.END_DESIGNATOR);
    }

    /**
     * Saves this {@code RunwayEnd} object into a {@link CacheEntry}.
     *
     * @param prefix the prefix for the cache entries
     * @param entry  the entry being saved to
     */
    @Override
    public void saveToCache(String prefix, CacheEntry entry) {

        entry.put(prefix + RunwayCacheUtils.END_DESIGNATOR, getDesignator());
    }
}
