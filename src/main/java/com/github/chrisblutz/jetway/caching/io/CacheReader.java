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

package com.github.chrisblutz.jetway.caching.io;


import com.github.chrisblutz.jetway.caching.Cache;
import com.github.chrisblutz.jetway.caching.CacheEntry;
import com.github.chrisblutz.jetway.caching.CacheException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.Properties;

/**
 * This class is used to handle the reading and
 * loading of cache files.
 *
 * @param <K> the cache key type
 * @param <V> the cache value type
 * @author Christopher Lutz
 */
public class CacheReader<K, V> {

    private static Logger logger = null;

    private Cache<K, V> cache;

    /**
     * Creates a new {@code CacheReader} with the specified
     * underlying cache.
     *
     * @param cache the cache to load to
     */
    public CacheReader(Cache<K, V> cache) {

        this.cache = cache;
    }

    private static Logger getLogger() {

        if (logger == null) {

            logger = LogManager.getLogger("Cache I/O");
        }

        return logger;
    }

    /**
     * Reads the cache entry for the specified key.
     *
     * @param key the key for the cache entry
     * @return The loaded entry for the key
     */
    public V read(K key) {

        try {

            File cacheFile = new File(cache.getCacheDirectory(), URLEncoder.encode(cache.getConverter().saveKey(key), "UTF-8") + ".cache");

            if (!cacheFile.exists()) {

                throw CacheException.forNonexistentCacheEntry(key.toString());
            }

            FileInputStream fin = new FileInputStream(cacheFile);
            Properties properties = new Properties();
            properties.load(fin);
            fin.close();

            CacheEntry entry = copyIntoCacheEntry(properties);
            return cache.getConverter().load(entry);

        } catch (Exception e) {

            getLogger().error("Failed to read entry from cache in " + cache.getCacheDirectory().getPath() + " for key " + key.toString() + ".");
            throw new CacheException("Failed to read entry from cache in " + cache.getCacheDirectory().getPath() + " for key " + key.toString() + ".");
        }
    }

    private CacheEntry copyIntoCacheEntry(Properties properties) {

        CacheEntry entry = new CacheEntry();
        for (Object key : properties.keySet()) {

            entry.asMap().put(key.toString(), properties.get(key).toString());
        }

        return entry;
    }
}
