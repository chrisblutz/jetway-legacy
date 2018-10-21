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

package com.github.chrisblutz.jetway.caching;

/**
 * This interface represents a type that is not
 * directly cacheable but can be cached within another
 * object.
 *
 * @author Christopher Lutz
 */
public interface CacheAppendable {

    /**
     * Loads data from a {@link CacheEntry} into an object.
     *
     * @param prefix the prefix for the cache entries
     * @param entry  the cache entry being loaded
     */
    void loadFromCache(String prefix, CacheEntry entry);

    /**
     * Saves an object into a {@link CacheEntry}.
     *
     * @param prefix the prefix for the cache entries
     * @param entry  the entry being saved to
     */
    void saveToCache(String prefix, CacheEntry entry);
}
