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
 * Represents a converter for cached values from {@link CacheEntry}
 * instances into objects and vice versa.
 *
 * @param <K> the cache key type
 * @param <V> the cache value type
 * @author Christopher Lutz
 */
public abstract class CacheConverter<K, V> {

    /**
     * Loads an object from the {@link CacheEntry}.
     *
     * @param entry the entry to load from
     * @return The loaded object
     */
    public abstract V load(CacheEntry entry);

    /**
     * Saves an object into a {@link CacheEntry}.
     *
     * @param object the object to save
     * @param entry  the entry to save to
     */
    public abstract void save(V object, CacheEntry entry);

    /**
     * Loads a cache key from a {@link String}.
     *
     * @param str the string to load from
     * @return The cache key
     */
    public abstract K loadKey(String str);

    /**
     * Saves a cache key to a {@link String}.
     *
     * @param key the key to save
     * @return The key as a string
     */
    public abstract String saveKey(K key);

    /**
     * Loads persistent data about a cached object from a {@link String}.
     *
     * @param key  the key for the object
     * @param data the persistent data as a {@link String}
     */
    public abstract void loadPersistentData(K key, String data);

    /**
     * Saves persistent data about a cached object to a {@link String}.
     *
     * @param value the object to save data for
     * @return The persistent data as a {@link String}
     */
    public abstract String savePersistentData(V value);
}
