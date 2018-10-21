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
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;

/**
 * This class is used to handle the writing and
 * saving of cache files.
 *
 * @param <K> the cache key type
 * @param <V> the cache value type
 * @author Christopher Lutz
 */
public class CacheWriter<K, V> {

    private static Logger logger = null;

    private Cache<K, V> cache;

    /**
     * Creates a new {@code CacheWriter} with the specified
     * underlying cache.
     *
     * @param cache the cache to save from
     */
    public CacheWriter(Cache<K, V> cache) {

        this.cache = cache;
    }

    private static Logger getLogger() {

        if (logger == null) {

            logger = LogManager.getLogger("Cache I/O");
        }

        return logger;
    }

    /**
     * Writes the cache entry with the specified key
     *
     * @param key the key for the cache entry
     */
    public void write(K key) {

        if (cache.getCacheDirectory() == null) {

            throw CacheException.forNullValue("Cache directory");
        }

        try {

            if (!cache.getCacheDirectory().exists() && !cache.getCacheDirectory().mkdirs()) {

                getLogger().error("Could not create cache directory in " + cache.getCacheDirectory().getPath() + ".");
                throw new CacheException("Could not create cache directory in " + cache.getCacheDirectory().getPath() + ".");
            }

            File cacheFile = getCachedFile(key);

            CacheEntry entry = new CacheEntry();
            cache.getConverter().save(cache.get(key), entry);

            Properties properties = new Properties();
            properties.putAll(entry.asMap());

            FileOutputStream fOut = new FileOutputStream(cacheFile);
            properties.store(fOut, null);
            fOut.close();

        } catch (Exception e) {

            getLogger().error("Failed to write entry to cache in " + cache.getCacheDirectory().getPath() + " for key " + key.toString() + ".");
            throw new CacheException("Failed to write entry to cache in " + cache.getCacheDirectory().getPath() + " for key " + key.toString() + ".");
        }
    }

    /**
     * Retrieves the cache file for the specified key.
     *
     * @param key the key for the cache entry
     * @return The file for the cache entry
     */
    public File getCachedFile(K key) {

        try {

            return new File(cache.getCacheDirectory(), URLEncoder.encode(cache.getConverter().saveKey(key), "UTF-8") + ".cache");

        } catch (UnsupportedEncodingException e) {

            // Ignore, method should never get here
            return null;
        }
    }
}
