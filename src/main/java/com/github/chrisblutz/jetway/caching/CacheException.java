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
 * An exception used to indicate an error while caching data or
 * loading cached information.
 *
 * @author Christopher Lutz
 */
public class CacheException extends RuntimeException {

    /**
     * Creates a new {@code CacheException} with the specified
     * message.
     *
     * @param message the message
     */
    public CacheException(String message) {

        super(message);
    }

    /**
     * Creates a new {@code CacheException} with the specified
     * message and cause.
     *
     * @param message the message
     * @param cause   the cause
     */
    public CacheException(String message, Exception cause) {

        super(message, cause);
    }

    /**
     * Retrieves a {@code CacheException} that indicates a value was of the incorrect type
     * while loading.
     *
     * @param type the expected type
     * @param key  the key for the offending value
     * @return The resulting exception
     */
    public static CacheException forInvalidTypeOnLoad(Class<?> type, String key) {

        Cache.getLogger().error("A value of type " + type.getSimpleName() + " was not found for key '" + key + "'.");
        return new CacheException("A value of type " + type.getSimpleName() + " was not found for key '" + key + "'.");
    }

    /**
     * Retrieves a {@code CacheException} that indicates a value was null.
     *
     * @param description the description of the value
     * @return The resulting exception
     */
    public static CacheException forNullValue(String description) {

        Cache.getLogger().error(description + " cannot be null.");
        return new CacheException(description + " cannot be null.");
    }

    /**
     * Retrieves a {@code CacheException} that indicates a value was nonexistent for
     * a specific key.
     *
     * @param key the key for the nonexistent value
     * @return The resulting exception
     */
    public static CacheException forNonexistentCacheEntry(String key) {

        Cache.getLogger().error("Cache entry for key '" + key + "' does not exist.");
        return new CacheException("Cache entry for key '" + key + "' does not exist.");
    }

    /**
     * Retrieves a {@code CacheException} that indicates an error occurred while saving
     * the main cache file.
     *
     * @param e the underlying cause
     * @return The resulting exception
     */
    public static CacheException forMainSaveFail(Exception e) {

        Cache.getLogger().error("Failed to save cache data file.", e);
        return new CacheException("Failed to save cache data file.", e);
    }

    /**
     * Retrieves a {@code CacheException} that indicates an error occurred while loading
     * the main cache file.
     *
     * @param e the underlying cause
     * @return The resulting exception
     */
    public static CacheException forMainLoadFail(Exception e) {

        Cache.getLogger().error("Failed to load cache data file.", e);
        return new CacheException("Failed to load cache data file.", e);
    }
}
