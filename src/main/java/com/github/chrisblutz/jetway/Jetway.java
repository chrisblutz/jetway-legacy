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

package com.github.chrisblutz.jetway;

import com.github.chrisblutz.jetway.aixm.AIXM;
import com.github.chrisblutz.jetway.caching.Cache;
import com.github.chrisblutz.jetway.caching.features.AirportCacheUtils;
import com.github.chrisblutz.jetway.caching.features.RunwayCacheUtils;
import com.github.chrisblutz.jetway.exceptions.JetwayException;
import com.github.chrisblutz.jetway.features.Airport;
import com.github.chrisblutz.jetway.features.Runway;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.UUID;

/**
 * This class serves as the main entry point for Jetway's API.
 *
 * @author Christopher Lutz
 */
public final class Jetway {

    private static final Cache<UUID, Airport> AIRPORT_CACHE = new Cache<>(10, AirportCacheUtils.getAirportConverter());
    private static final Cache<UUID, Runway> RUNWAY_CACHE = new Cache<>(50, RunwayCacheUtils.getRunwayConverter());

    private static final File CACHE_DIRECTORY = new File(".jetway/cache/");
    private static final File AIRPORT_CACHE_DIRECTORY = new File(CACHE_DIRECTORY, "apt");
    private static final File RUNWAY_CACHE_DIRECTORY = new File(CACHE_DIRECTORY, "rwy");

    private static File nasrLocation;
    private static boolean initialized = false;

    private static Logger logger = null;

    private Jetway() {

    }

    /**
     * Sets the location of the FAA NASR subscription file (distributed as
     * a zip file).
     *
     * @param nasrLocation The location of the zipped NASR file
     */
    public static void stageNASRFile(File nasrLocation) {

        Jetway.nasrLocation = nasrLocation;
    }

    /**
     * Gets the location of the FAA NASR subscription file.  If one has not
     * been set, this method returns {@code null}.
     *
     * @return The location of the NASR file
     */
    public static File getNASRFileLocation() {

        return nasrLocation;
    }

    /**
     * Initializes Jetway's cache data and generates it from the NASR source
     * data if the cache is invalid or nonexistent.
     * <p>
     * This method is equivalent to calling
     * <pre>
     *     Jetway.initialize(true);
     * </pre>
     *
     * @throws JetwayException if an error occurs while Jetway is initializing
     *                         its caches.
     * @see Jetway#initialize(boolean)
     */
    public static void initialize() throws JetwayException {

        initialize(true);
    }

    /**
     * Initializes Jetway's cache data.  If the {@code useCache} parameter is true,
     * data will be loaded from Jetway's cached files (if they exist).  If it is
     * {@code false}, any existing cached data will be ignored and the entire cache
     * will be rebuilt from the source NASR data.
     *
     * @param useCache a {@code boolean} value indicating if Jetway should attempt
     *                 to use existing cached data or completely rebuild the cache
     *                 from source NASR data.  If existing cached data does not exist,
     *                 this parameter has no effect.
     * @throws JetwayException if an error occurs while Jetway is initializing
     *                         its caches.
     */
    public static void initialize(boolean useCache) throws JetwayException {

        if (!initialized) {

            initialized = true;

            getLogger().info("Initializing Jetway, " + (useCache ? "using cached data" : "ignoring cached data") + "...");

            boolean needsInit;
            AIRPORT_CACHE.setCacheVersion(1);
            AIRPORT_CACHE.setCacheDirectory(AIRPORT_CACHE_DIRECTORY);

            getLogger().info("Initializing airport cache...");
            getLogger().info("    Airport Cache Version:   " + AIRPORT_CACHE.getCacheVersion());
            getLogger().info("    Airport Cache Directory: " + AIRPORT_CACHE.getCacheDirectory().getPath());

            needsInit = !AIRPORT_CACHE.initialize(useCache);

            getLogger().info(needsInit ? "Airport cache is invalid or nonexistent." : "Airport cache is valid.");

            RUNWAY_CACHE.setCacheVersion(1);
            RUNWAY_CACHE.setCacheDirectory(RUNWAY_CACHE_DIRECTORY);

            getLogger().info("Initializing runway cache...");
            getLogger().info("    Runway Cache Version:   " + RUNWAY_CACHE.getCacheVersion());
            getLogger().info("    Runway Cache Directory: " + RUNWAY_CACHE.getCacheDirectory().getPath());

            boolean runwayInit = !RUNWAY_CACHE.initialize(useCache);

            getLogger().info(runwayInit ? "Runway cache is invalid or nonexistent." : "Runway cache is valid.");

            needsInit = runwayInit || needsInit;

            if (needsInit) {

                getLogger().info("Caching AIXM data from source...");

                getLogger().info("Invalidating airport cache...");
                AIRPORT_CACHE.invalidate();

                getLogger().info("Invalidating runway cache...");
                RUNWAY_CACHE.invalidate();

                AIXM.buildCache();
            }

            getLogger().info("Initialization completed successfully.");

        } else {

            getLogger().warn("Initialization attempted when Jetway has already been initialized.");
        }
    }

    /**
     * Determines if Jetway has been initialized by calling either
     * {@link Jetway#initialize()} or {@link Jetway#initialize(boolean)}.
     *
     * @return {@code true} if Jetway has been initialized, {@code false}
     * otherwise.
     */
    public static boolean isInitialized() {

        return initialized;
    }

    /**
     * Unloads and saves all cached data and empties all persistent cache data.
     * <p>
     * After this method is called, Jetway must be reinitialized using
     * {@link Jetway#initialize()} or {@link Jetway#initialize(boolean)}
     * before cached data can be used again.
     */
    public static void unload() {

        Jetway.getLogger().info("Unloading Jetway...");

        initialized = false;

        Jetway.getLogger().info("Uninitializing airport cache...");
        AIRPORT_CACHE.uninitialize();

        Jetway.getLogger().info("Uninitializing runway cache...");
        RUNWAY_CACHE.uninitialize();
    }

    /**
     * Gets the cache that manages {@link Airport} instances and their
     * {@link UUID} keys.
     *
     * @return The cache that manages {@link Airport} instances
     */
    public static Cache<UUID, Airport> getAirportCache() {

        return AIRPORT_CACHE;
    }

    /**
     * Gets the cache that manages {@link Runway} instances and their
     * {@link UUID} keys.
     *
     * @return The cache that manages {@link Runway} instances
     */
    public static Cache<UUID, Runway> getRunwayCache() {

        return RUNWAY_CACHE;
    }

    private static Logger getLogger() {

        if (logger == null) {

            logger = LogManager.getLogger("Jetway");
            JetwayInfo.logInformation(logger);
        }

        return logger;
    }
}
