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

import java.util.HashMap;
import java.util.Map;

/**
 * Represents cache data being loaded or saved from a cache file.
 *
 * @author Christopher Lutz
 */
public class CacheEntry {

    private Map<String, String> map = new HashMap<>();

    /**
     * Gets a value from the cache entry.
     *
     * @param key the entry key
     * @return The value for the key
     */
    public String get(String key) {

        String str = map.get(key);
        if (str != null && !str.equals("null")) {

            return str;

        } else {

            return null;
        }
    }

    /**
     * Gets a {@code boolean} value from the cache entry.
     *
     * @param key the entry key
     * @return The value for the key
     * @throws CacheException if the value's type is invalid
     */
    public boolean getBoolean(String key) {

        try {

            return Boolean.parseBoolean(get(key));

        } catch (Exception e) {

            throw CacheException.forInvalidTypeOnLoad(Boolean.class, key);
        }
    }

    /**
     * Gets a {@code byte} value from the cache entry.
     *
     * @param key the entry key
     * @return The value for the key
     * @throws CacheException if the value's type is invalid
     */
    public byte getByte(String key) {

        try {

            return Byte.parseByte(get(key));

        } catch (Exception e) {

            throw CacheException.forInvalidTypeOnLoad(Byte.class, key);
        }
    }

    /**
     * Gets a {@code char} value from the cache entry.
     *
     * @param key the entry key
     * @return The value for the key
     * @throws CacheException if the value's type is invalid
     */
    public char getCharacter(String key) {

        String value = get(key);
        if (value != null && value.length() == 1) {

            return value.charAt(0);

        } else {

            throw CacheException.forInvalidTypeOnLoad(Boolean.class, key);
        }
    }

    /**
     * Gets a {@code double} value from the cache entry.
     *
     * @param key the entry key
     * @return The value for the key
     * @throws CacheException if the value's type is invalid
     */
    public double getDouble(String key) {

        try {

            return Double.parseDouble(get(key));

        } catch (Exception e) {

            throw CacheException.forInvalidTypeOnLoad(Double.class, key);
        }
    }

    /**
     * Gets a {@code float} value from the cache entry.
     *
     * @param key the entry key
     * @return The value for the key
     * @throws CacheException if the value's type is invalid
     */
    public float getFloat(String key) {

        try {

            return Float.parseFloat(get(key));

        } catch (Exception e) {

            throw CacheException.forInvalidTypeOnLoad(Float.class, key);
        }
    }

    /**
     * Gets a {@code int} value from the cache entry.
     *
     * @param key the entry key
     * @return The value for the key
     * @throws CacheException if the value's type is invalid
     */
    public int getInteger(String key) {

        try {

            return Integer.parseInt(get(key));

        } catch (Exception e) {

            throw CacheException.forInvalidTypeOnLoad(Integer.class, key);
        }
    }

    /**
     * Gets a {@code long} value from the cache entry.
     *
     * @param key the entry key
     * @return The value for the key
     * @throws CacheException if the value's type is invalid
     */
    public long getLong(String key) {

        try {

            return Long.parseLong(get(key));

        } catch (Exception e) {

            throw CacheException.forInvalidTypeOnLoad(Long.class, key);
        }
    }

    /**
     * Gets a {@code short} value from the cache entry.
     *
     * @param key the entry key
     * @return The value for the key
     * @throws CacheException if the value's type is invalid
     */
    public short getShort(String key) {

        try {

            return Short.parseShort(get(key));

        } catch (Exception e) {

            throw CacheException.forInvalidTypeOnLoad(Short.class, key);
        }
    }

    /**
     * Puts a value into this entry and associates it
     * with the specified key.
     *
     * @param key   the key for the entry
     * @param value the value
     */
    public void put(String key, Object value) {

        map.put(key, value == null ? "null" : value.toString());
    }

    /**
     * Retrieves the underlying map for this {@code CacheEntry}.
     *
     * @return The underlying map for this {@code CacheEntry}
     */
    public Map<String, String> asMap() {

        return map;
    }
}
