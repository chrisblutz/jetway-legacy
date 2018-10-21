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


import com.github.chrisblutz.jetway.caching.io.CacheReader;
import com.github.chrisblutz.jetway.caching.io.CacheWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

/**
 * Represents a cache of objects that can be loaded from
 * and saved to files.
 *
 * @param <K> the cache key type
 * @param <V> the cache value type
 * @author Christopher Lutz
 */
public class Cache<K, V> {

    private static final String CACHE_FILENAME = ".cache";
    private static final String CACHE_VERSION = "CacheVersion";
    private static final String INVALIDATION_TIME = "InvalidateAfter";
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("MM/dd/YYYY k:mm:ss:SSSZ", Locale.ENGLISH);

    private static Logger logger = null;

    private int maxSize;
    private Map<K, V> map;
    private Map<K, String> persistentData;
    private List<K> accessList;

    private File cacheDirectory = null;
    private CacheConverter<K, V> converter;

    private CacheReader<K, V> reader;
    private CacheWriter<K, V> writer;

    private boolean updated = false;
    private int cacheVersion = -1;
    private Date invalidationDate = new Date(Long.MAX_VALUE);

    /**
     * Creates a new {@code Cache} with the specified {@link CacheConverter}
     * to convert values to cache entries.
     *
     * @param converter the converter
     */
    public Cache(CacheConverter<K, V> converter) {

        this(100, converter);
    }

    /**
     * Creates a new {@code Cache} with the specified maximum size and
     * specified {@link CacheConverter} to convert values to cache entries.
     *
     * @param size      the maximum size of the cache
     * @param converter the converter
     */
    public Cache(int size, CacheConverter<K, V> converter) {

        maxSize = size;
        map = new HashMap<>(size + 1, 1);
        persistentData = new HashMap<>();
        accessList = new ArrayList<>(size + 1);

        this.converter = converter;

        reader = new CacheReader<>(this);
        writer = new CacheWriter<>(this);
    }

    static Logger getLogger() {

        if (logger == null) {

            logger = LogManager.getLogger("Cache");
        }

        return logger;
    }

    /**
     * Gets the cache directory for this cache.
     *
     * @return The cache directory for this cache
     */
    public File getCacheDirectory() {

        return cacheDirectory;
    }

    /**
     * Sets the cache directory for this cache.
     *
     * @param cacheDirectory the cache directory
     */
    public void setCacheDirectory(File cacheDirectory) {

        this.cacheDirectory = cacheDirectory;
    }

    /**
     * Gets the {@link CacheConverter} for this cache.
     *
     * @return The {@link CacheConverter} for this cache
     */
    public CacheConverter<K, V> getConverter() {

        return converter;
    }

    /**
     * Gets the version of this cache.
     *
     * @return The version of this cache
     */
    public int getCacheVersion() {

        return cacheVersion;
    }

    /**
     * Sets the version of this cache
     *
     * @param cacheVersion the cache version
     */
    public void setCacheVersion(int cacheVersion) {

        this.cacheVersion = cacheVersion;
    }

    /**
     * Gets the date this cache should be invalidated.
     *
     * @return The invalidation date of this cache
     */
    public Date getInvalidationDate() {

        return invalidationDate;
    }

    /**
     * Sets the date this cache should be invalidated.
     *
     * @param invalidationDate the invalidation date
     */
    public void setInvalidationDate(Date invalidationDate) {

        this.invalidationDate = invalidationDate;
    }

    /**
     * Initializes this cache from data saved in the cache
     * directory.
     *
     * @return {@code true} if the cache load was successful,
     * {@code false} otherwise.  A {@code false} value indicates
     * the need for data to be reloaded into the cache from the
     * data's source.
     */
    public boolean initialize() {

        return initialize(true);
    }

    /**
     * Initializes this cache from data saved in the cache
     * directory if indicated.
     *
     * @param useExisting whether or not the cache should use existing data
     * @return {@code true} if the cache load was successful,
     * {@code false} otherwise.  A {@code false} value indicates
     * the need for data to be reloaded into the cache from the
     * data's source.
     */
    public boolean initialize(boolean useExisting) {

        if (useExisting) {

            try {

                File mainCache = getMainCacheFile();
                if (mainCache.exists()) {

                    Scanner sc = new Scanner(mainCache);
                    boolean valid = validateCacheInformation(sc);
                    if (valid) {

                        while (sc.hasNextLine()) {

                            String line = sc.nextLine();
                            String[] parts = line.split("=", 2);
                            int keyLength = Integer.parseInt(parts[0]);
                            String keyStr = parts[1].substring(0, keyLength);
                            K key = getConverter().loadKey(keyStr);
                            String value = parts[1].substring(keyLength);
                            value = value.isEmpty() ? null : value;
                            getConverter().loadPersistentData(key, value);
                            persistentData.put(key, value);
                        }
                    }
                    sc.close();

                    if (!valid) {

                        invalidate();
                        return false;
                    }

                    return true;

                } else {

                    return false;
                }

            } catch (Exception e) {

                throw CacheException.forMainLoadFail(e);
            }

        } else {

            invalidate();
            return false;
        }
    }

    private boolean validateCacheInformation(Scanner sc) {

        String cacheVersion = sc.nextLine().substring(CACHE_VERSION.length() + 1);
        if (Integer.parseInt(cacheVersion) != getCacheVersion()) {

            return false;
        }

        try {

            String invalidationTime = sc.nextLine().substring(INVALIDATION_TIME.length() + 1);
            Date invalidationDate = DATE_FORMATTER.parse(invalidationTime);
            if (DATE_FORMATTER.parse(invalidationTime).before(Date.from(Instant.now()))) {

                return false;
            }

            setInvalidationDate(invalidationDate);

        } catch (Exception e) {

            return false;
        }

        return true;
    }

    /**
     * Uninitializes this cache, emptying all data to files
     * and saving main cache files.
     */
    public void uninitialize() {

        emptyAll();

        if (updated) {

            try {

                File mainCache = getMainCacheFile();
                if (!getCacheDirectory().exists() && !getCacheDirectory().mkdirs()) {

                    getLogger().error("Could not create cache directory in " + getCacheDirectory().getPath() + ".");
                    throw new CacheException("Could not create cache directory in " + getCacheDirectory().getPath() + ".");
                }

                PrintStream stream = new PrintStream(mainCache);
                writeCacheInformation(stream);
                writePersistentData(stream);
                stream.close();

            } catch (Exception e) {

                throw CacheException.forMainSaveFail(e);
            }
        }
    }

    private void writeCacheInformation(PrintStream stream) {

        stream.println(CACHE_VERSION + ":" + getCacheVersion());
        stream.println(INVALIDATION_TIME + ":" + DATE_FORMATTER.format(getInvalidationDate()));
    }

    private void writePersistentData(PrintStream stream) {

        for (K key : persistentData.keySet()) {

            String keyStr = getConverter().saveKey(key);
            int length = keyStr.length();
            String data = persistentData.get(key);
            data = data == null ? "" : data;
            String entry = length + "=" + keyStr + data;
            stream.println(entry);
        }
    }

    private File getMainCacheFile() {

        return new File(getCacheDirectory(), CACHE_FILENAME);
    }

    /**
     * Invalidates this cache, removing all cache files and loaded data.
     * Data will need to be reloaded from the initial source before
     * this cache can be used again.
     */
    public void invalidate() {

        if (!deleteCacheFiles()) {

            getLogger().warn("Failed to invalidate cache in " + getCacheDirectory().getPath() + ".");
        }

        accessList.clear();
        map.clear();
        persistentData.clear();
    }

    private boolean deleteCacheFiles() {

        if (getCacheDirectory().exists()) {

            File[] allFiles = getCacheDirectory().listFiles();
            if (allFiles != null) {

                for (File file : allFiles) {

                    if (!file.delete()) {

                        return false;
                    }
                }
            }

            return getCacheDirectory().delete();

        } else {

            return true;
        }
    }

    /**
     * Gets the value associated with the specified key,
     * loading it if required.
     *
     * @param key the value's key
     * @return The loaded value
     */
    public V get(K key) {

        if (!map.containsKey(key)) {

            K oldest = removeOldest();

            if (oldest != null) {

                forceUnload(oldest);
            }

            V value = reader.read(key);
            map.put(key, value);
        }

        addAccessEntry(key);

        return map.get(key);
    }

    /**
     * Retrieves a {@link CacheLink} to the specified value
     * in this cache.  The value does not need to be loaded
     * to link to it.
     *
     * @param key the value's key
     * @return The resulting {@link CacheLink}
     */
    public CacheLink<K, V> linkTo(K key) {

        return new CacheLink<>(this, key);
    }

    /**
     * Empties all loaded entries and saves them
     * to their respective files.
     */
    @SuppressWarnings("unchecked")
    public void emptyAll() {

        K[] keys = (K[]) accessList.toArray();
        for (K key : keys) {

            forceUnload(key);
        }
    }

    /**
     * Unloads a value from this cache, saving it
     * to its respective file.
     *
     * @param key the value's key
     */
    public void forceUnload(K key) {

        writer.write(key);
        String data = getConverter().savePersistentData(map.get(key));
        if (!persistentData.containsKey(key) || !persistentData.get(key).equals(data)) {

            persistentData.put(key, data);
            updated = true;
        }

        accessList.remove(key);
        map.remove(key);
    }

    /**
     * Adds an object to this cache.
     *
     * @param key   the value's key
     * @param value the value
     */
    public void add(K key, V value) {

        K oldest = removeOldest();
        if (oldest != null) {

            forceUnload(oldest);
        }

        map.put(key, value);
        addAccessEntry(key);
        getConverter().loadPersistentData(key, getConverter().savePersistentData(value));
        updated = true;
    }

    /**
     * Removes an object from this cache, deleting
     * its cached file in the process.
     *
     * @param key the value's key
     */
    public void remove(K key) {

        forceUnload(key);
        File cachedFile = writer.getCachedFile(key);
        persistentData.remove(key);
        if (cachedFile.exists() && !cachedFile.delete()) {

            getLogger().warn("Failed to remove file from " + getCacheDirectory().getPath() + " for key " + key.toString() + ".");
        }
        updated = true;
    }

    private void addAccessEntry(K key) {

        accessList.remove(key);
        accessList.add(key);
    }

    private K removeOldest() {

        if (accessList.size() >= maxSize) {

            return accessList.remove(0);

        } else {

            return null;
        }
    }
}
