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

package com.github.chrisblutz.jetway.caching.features;

import com.github.chrisblutz.jetway.caching.CacheConverter;
import com.github.chrisblutz.jetway.caching.CacheEntry;
import com.github.chrisblutz.jetway.features.Airport;
import com.github.chrisblutz.jetway.features.Airports;

import java.util.UUID;

/**
 * This is a utility class for Jetway's airport cache.
 *
 * @author Christopher Lutz
 */
public class AirportCacheUtils {

    /**
     * The key for airport names
     */
    public static final String NAME = "name";
    /**
     * The key for airport IATA designators
     */
    public static final String IATA_DESIGNATOR = "iataDesignator";
    /**
     * The key for airport ICAO identifiers
     */
    public static final String ICAO_IDENTIFIER = "icaoIdentifier";
    /**
     * The key for airport FAA site numbers
     */
    public static final String SITE_NUMBER = "siteNumber";
    /**
     * The key for airport public use availability
     */
    public static final String IS_PUBLIC = "publicUse";
    /**
     * The key for airport elevations
     */
    public static final String ELEVATION = "elevation";
    /**
     * The key for airport land areas
     */
    public static final String LAND_AREA = "landArea";
    /**
     * The key for airport latitudes
     */
    public static final String LATITUDE = "latitude";
    /**
     * The key for airport longitudes
     */
    public static final String LONGITUDE = "longitude";
    /**
     * The key for airport ownership types
     */
    public static final String OWNERSHIP = "ownership";
    /**
     * The key for airport facility types
     */
    public static final String FACILITY_TYPE = "facilityType";
    /**
     * The key for based single-engine aircraft
     */
    public static final String BASED_SINGLE_ENGINE_AIRCRAFT = "basedSingleEngineAircraft";
    /**
     * The key for based multi-engine aircraft
     */
    public static final String BASED_MULTI_ENGINE_AIRCRAFT = "basedMultiEngineAircraft";
    /**
     * The key for based jet engine aircraft
     */
    public static final String BASED_JET_ENGINE_AIRCRAFT = "basedJetEngineAircraft";
    /**
     * The key for based helicopters
     */
    public static final String BASED_HELICOPTERS = "basedHelicopters";
    /**
     * The key for based gliders
     */
    public static final String BASED_GLIDERS = "basedGliders";
    /**
     * The key for based military aircraft
     */
    public static final String BASED_MILITARY_AIRCRAFT = "basedMilitaryAircraft";
    /**
     * The key for based ultralight aircraft
     */
    public static final String BASED_ULTRALIGHT_AIRCRAFT = "basedUltralightAircraft";

    /**
     * The key for airport runway keys
     */
    public static final String RUNWAYS = "runways";

    private static CacheConverter<UUID, Airport> airportConverter = null;

    /**
     * Retrieves the {@link CacheConverter} for {@link Airport} instances.
     *
     * @return The airport cache converter
     */
    public static CacheConverter<UUID, Airport> getAirportConverter() {

        if (airportConverter == null) {

            airportConverter = new CacheConverter<UUID, Airport>() {

                @Override
                public Airport load(CacheEntry entry) {

                    return Airport.loadAirportFromCache(entry);
                }

                @Override
                public void save(Airport airport, CacheEntry entry) {

                    airport.saveToCache(entry);
                }

                @Override
                public UUID loadKey(String str) {

                    return UUID.fromString(str);
                }

                @Override
                public String saveKey(UUID key) {

                    return key.toString();
                }

                @Override
                public void loadPersistentData(UUID key, String data) {

                    String[] parts = data.split("=", 2);
                    String[] lengths = parts[0].split(",");
                    data = parts[1];

                    int nameLength = Integer.parseInt(lengths[0]);
                    String name = data.substring(0, nameLength);
                    data = data.substring(nameLength);

                    int designatorLength = Integer.parseInt(lengths[1]);
                    String designator = data.substring(0, designatorLength);
                    data = data.substring(designatorLength);

                    int identifierLength = Integer.parseInt(lengths[2]);
                    String identifier = data.substring(0, identifierLength);

                    Airports.register(key, name, designator, identifier);
                }

                @Override
                public String savePersistentData(Airport airport) {

                    String name = airport.getName();
                    String designator = airport.getIATADesignator();
                    String identifier = airport.getICAOIdentifier();
                    int nameLength = name.length();
                    int designatorLength = designator.length();
                    int identifierLength = identifier == null ? 0 : identifier.length();

                    return nameLength + "," + designatorLength + "," + identifierLength + "=" + name + designator + (identifier == null ? "" : identifier);
                }
            };
        }

        return airportConverter;
    }
}
