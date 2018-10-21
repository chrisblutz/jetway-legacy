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
 * Represents a link to a value in a cache.  Having a link
 * to a value does not mean that the value in question is
 * actually loaded.
 *
 * @param <K> the cache key type
 * @param <V> the cache value type
 * @author Christopher Lutz
 */
public class CacheLink<K, V> {

    private Cache<K, V> cache;
    private K key;

    /**
     * Creates a new {@code CacheLink} with the specified key
     * linking to the specified cache.
     *
     * @param cache the cache to link to
     * @param key   the key in the cache to link to
     */
    public CacheLink(Cache<K, V> cache, K key) {

        this.cache = cache;
        this.key = key;
    }

    /**
     * Returns the key associated with this link
     *
     * @return the linked key
     */
    public K key() {

        return key;
    }

    /**
     * Loads the value from the cache for the linked key
     *
     * @return The loaded value for this link
     */
    public V get() {

        return cache.get(key);
    }
}
