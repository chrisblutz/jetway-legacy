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

package com.github.chrisblutz.jetway.features;

import com.github.chrisblutz.jetway.Jetway;
import com.github.chrisblutz.jetway.aixm.AIXMAssignable;
import com.github.chrisblutz.jetway.aixm.AIXMFeature;
import com.github.chrisblutz.jetway.aixm.AIXMType;
import com.github.chrisblutz.jetway.caching.CacheEntry;
import com.github.chrisblutz.jetway.caching.CacheLink;
import com.github.chrisblutz.jetway.caching.Cacheable;
import com.github.chrisblutz.jetway.caching.features.AirportCacheUtils;
import com.github.chrisblutz.jetway.features.airports.FacilityType;
import com.github.chrisblutz.jetway.features.airports.Ownership;
import com.github.chrisblutz.jetway.features.positioning.GeoCoordinate;

import java.util.*;

/**
 * Represents an airport or a heliport.
 *
 * @author Christopher Lutz
 */
public class Airport implements AIXMAssignable<Runway>, Cacheable {

    private String name, iataDesignator, icaoIdentifier, siteNumber;
    private boolean isPublic;
    private double elevation, landArea;
    private GeoCoordinate geoPosition;
    private Ownership ownership;
    private FacilityType facilityType;
    private int singleEngineAircraft, multiEngineAircraft, jetEngineAircraft, helicopters, gliders, militaryAircraft, ultralightAircraft;

    private List<CacheLink<UUID, Runway>> runwayLinks = new ArrayList<>();
    private Map<String, CacheLink<UUID, Runway>> runwayMappings = new HashMap<>();

    /**
     * Loads an {@code Airport} instance based on the specified {@link CacheEntry}.
     *
     * @param entry the entry being loaded
     * @return The resulting {@code Airport} instance
     */
    public static Airport loadAirportFromCache(CacheEntry entry) {

        Airport airport = new Airport();
        airport.loadFromCache(entry);
        return airport;
    }

    /**
     * Retrieves the name of this airport.
     *
     * @return The name of this airport
     */
    public String getName() {

        return name;
    }

    /**
     * Retrieves the designator of this airport assigned by
     * the International Air Transport Authority (IATA).
     *
     * @return This airport's IATA designator
     */
    public String getIATADesignator() {

        return iataDesignator;
    }

    /**
     * Retrieves the identifier of this airport assigned by
     * the International Civil Aviation Organization (ICAO).
     * If this airport does not have an identifier, {@code null}
     * will be returned.
     *
     * @return This airport's ICAO identifier, or {@code null}
     * if it does not have one
     */
    public String getICAOIdentifier() {

        return icaoIdentifier;
    }

    /**
     * Retrieves the site number of this airport assigned by
     * the US Federal Aviation Administration (FAA).
     *
     * @return This airport's FAA site number
     */
    public String getSiteNumber() {

        return siteNumber;
    }

    /**
     * Checks if this airport is available to the public.
     *
     * @return {@code true} if this airport is publicly
     * available, {@code false} otherwise
     */
    public boolean isPublic() {

        return isPublic;
    }

    /**
     * Retrieves the elevation of this airport in feet.
     *
     * @return The elevation of this airport in feet
     */
    public double getElevation() {

        return elevation;
    }

    /**
     * Retrieves the land area covered by this airport in acres.
     *
     * @return The land area of this airport in acres
     */
    public double getLandArea() {

        return landArea;
    }

    /**
     * Retrieves the geographic position of this airport.
     *
     * @return The {@link GeoCoordinate} representing the
     * geographic location of this airport
     */
    public GeoCoordinate getGeographicPosition() {

        return geoPosition;
    }

    /**
     * Retrieves the ownership type of this airport
     * (military, private, public, etc.).
     *
     * @return The {@link Ownership} type of this airport
     */
    public Ownership getOwnership() {

        return ownership;
    }

    /**
     * Retrieves the type information for this airport
     * (heliport, airport, both, etc.).
     *
     * @return The {@link FacilityType} for this airport
     */
    public FacilityType getFacilityType() {

        return facilityType;
    }

    /**
     * Retrieves the number of single-engine aircraft based
     * at this airport.
     *
     * @return The number of single-engine aircraft based at this airport
     */
    public int getBasedSingleEngineAircraft() {

        return singleEngineAircraft;
    }

    /**
     * Retrieves the number of multi-engine aircraft based
     * at this airport.
     *
     * @return The number of multi-engine aircraft based at this airport
     */
    public int getBasedMultiEngineAircraft() {

        return multiEngineAircraft;
    }

    /**
     * Retrieves the number of jet engine aircraft based
     * at this airport.
     *
     * @return The number of jet engine aircraft based at this airport
     */
    public int getBasedJetEngineAircraft() {

        return jetEngineAircraft;
    }

    /**
     * Retrieves the number of helicopters based at this airport.
     *
     * @return The number of helicopters based at this airport
     */
    public int getBasedHelicopters() {

        return helicopters;
    }

    /**
     * Retrieves the number of gliders based at this airport.
     *
     * @return The number of gliders based at this airport
     */
    public int getBasedGliders() {

        return gliders;
    }

    /**
     * Retrieves the number of military aircraft based at this airport.
     *
     * @return The number of military aircraft based at this airport
     */
    public int getBasedMilitaryAircraft() {

        return militaryAircraft;
    }

    /**
     * Retrieves the number of ultralight aircraft based at this airport.
     *
     * @return The number of ultralight aircraft based at this airport
     */
    public int getBasedUltralightAircraft() {

        return ultralightAircraft;
    }

    /**
     * Loads all runways assigned to this airport and returns them
     * as an array.
     *
     * @return An array of all {@link Runway} instances assigned to
     * this airport
     */
    public Runway[] getRunways() {

        Runway[] runways = new Runway[runwayLinks.size()];
        for (int i = 0; i < runwayLinks.size(); i++) {

            runways[i] = runwayLinks.get(i).get();
        }
        return runways;
    }

    /**
     * Retrieves the designators for all runways assigned to this
     * airport and returns them as an array.  The specific
     * {@link Runway} instances do not need to be loaded when this
     * method is called.
     *
     * @return An array of designators for {@link Runway} instances
     * assigned to this airport
     */
    public String[] getRunwayDesignators() {

        String[] designators = new String[runwayLinks.size()];
        for (int i = 0; i < runwayLinks.size(); i++) {

            designators[i] = Runways.getDesignatorForUUID(runwayLinks.get(i).key());
        }
        return designators;
    }

    /**
     * Gets the {@link Runway} instance assigned to this airport that
     * has the specified designator, loading it if required.
     *
     * @param designator the runway designator
     * @return The loaded {@link Runway} corresponding to the designator,
     * or {@code null} if there is no assigned runway with the specified
     * designator
     */
    public Runway getRunwayForDesignator(String designator) {

        if (!runwayLinks.isEmpty() && runwayMappings.isEmpty()) {

            for (CacheLink<UUID, Runway> link : runwayLinks) {

                runwayMappings.put(Runways.getDesignatorForUUID(link.key()), link);
            }
        }

        if (runwayMappings.containsKey(designator)) {

            return runwayMappings.get(designator).get();

        } else {

            return null;
        }
    }

    /**
     * Assigns a loaded {@link Runway} to this airport based on its
     * {@link UUID} key.
     *
     * @param uuid  the {@link UUID} key
     * @param value the {@link Runway} object
     */
    @Override
    public void assign(UUID uuid, Runway value) {

        runwayLinks.add(Jetway.getRunwayCache().linkTo(uuid));
    }

    /**
     * Loads data from an {@link AIXMFeature} into this {@code Airport}
     * object.
     *
     * @param type    the type of feature currently being loaded.  This is
     *                useful if multiple feature types are loaded into the
     *                same class.
     * @param feature the feature data
     */
    @Override
    public void loadFromAIXM(AIXMType type, AIXMFeature feature) {

        name = feature.getString("AIXMName");
        iataDesignator = feature.getString("Designator");
        icaoIdentifier = feature.checkedCrawl("LocationIndicatorICAO").getString();
        siteNumber = feature.extension().getString("AirportSiteNumber");
        isPublic = !feature.getBoolean("PrivateUse");
        elevation = feature.getDouble("FieldElevation");
        landArea = feature.extension().getDouble("LandSize");
        geoPosition = feature.getGeographicCoordinate("ARP/ElevatedPoint/Position");
        ownership = feature.extension().getEnum("OwnershipType", Ownership.class);
        facilityType = feature.getEnum("Type", FacilityType.class);
        singleEngineAircraft = feature.extension().getInteger("NumberOfSingleEngineAircraft");
        multiEngineAircraft = feature.extension().getInteger("NumberOfMultiEngineAircraft");
        jetEngineAircraft = feature.extension().getInteger("NumberOfJetEngineAircraft");
        helicopters = feature.extension().getInteger("NumberOfHelicopter");
        gliders = feature.extension().getInteger("NumberOfOperationalGlider");
        militaryAircraft = feature.extension().getInteger("NumberOfMilitaryAircraft");
        ultralightAircraft = feature.extension().getInteger("NumberOfUltralightAircraft");
    }

    /**
     * Loads data from a {@link CacheEntry} into this {@code Airport} object.
     *
     * @param entry the cache entry being loaded
     */
    @Override
    public void loadFromCache(CacheEntry entry) {

        name = entry.get(AirportCacheUtils.NAME);
        iataDesignator = entry.get(AirportCacheUtils.IATA_DESIGNATOR);
        icaoIdentifier = entry.get(AirportCacheUtils.ICAO_IDENTIFIER);
        icaoIdentifier = icaoIdentifier.isEmpty() ? null : icaoIdentifier;
        siteNumber = entry.get(AirportCacheUtils.SITE_NUMBER);
        isPublic = entry.getBoolean(AirportCacheUtils.IS_PUBLIC);
        elevation = entry.getDouble(AirportCacheUtils.ELEVATION);
        landArea = entry.getDouble(AirportCacheUtils.LAND_AREA);
        double latitude = entry.getDouble(AirportCacheUtils.LATITUDE);
        double longitude = entry.getDouble(AirportCacheUtils.LONGITUDE);
        geoPosition = new GeoCoordinate(latitude, longitude);
        ownership = Ownership.valueOf(entry.get(AirportCacheUtils.OWNERSHIP));
        facilityType = FacilityType.valueOf(entry.get(AirportCacheUtils.FACILITY_TYPE));
        singleEngineAircraft = entry.getInteger(AirportCacheUtils.BASED_SINGLE_ENGINE_AIRCRAFT);
        multiEngineAircraft = entry.getInteger(AirportCacheUtils.BASED_MULTI_ENGINE_AIRCRAFT);
        jetEngineAircraft = entry.getInteger(AirportCacheUtils.BASED_JET_ENGINE_AIRCRAFT);
        helicopters = entry.getInteger(AirportCacheUtils.BASED_HELICOPTERS);
        gliders = entry.getInteger(AirportCacheUtils.BASED_GLIDERS);
        militaryAircraft = entry.getInteger(AirportCacheUtils.BASED_MILITARY_AIRCRAFT);
        ultralightAircraft = entry.getInteger(AirportCacheUtils.BASED_ULTRALIGHT_AIRCRAFT);

        String runways = entry.get(AirportCacheUtils.RUNWAYS);
        if (!runways.isEmpty()) {

            for (String runway : runways.split(",")) {

                runwayLinks.add(Jetway.getRunwayCache().linkTo(UUID.fromString(runway)));
            }
        }
    }

    /**
     * Saves this {@code Airport} object into a {@link CacheEntry}.
     *
     * @param entry the entry being saved to
     */
    @Override
    public void saveToCache(CacheEntry entry) {

        entry.put(AirportCacheUtils.NAME, getName());
        entry.put(AirportCacheUtils.IATA_DESIGNATOR, getIATADesignator());
        entry.put(AirportCacheUtils.ICAO_IDENTIFIER, getICAOIdentifier() == null ? "" : getICAOIdentifier());
        entry.put(AirportCacheUtils.SITE_NUMBER, getSiteNumber());
        entry.put(AirportCacheUtils.IS_PUBLIC, isPublic());
        entry.put(AirportCacheUtils.ELEVATION, getElevation());
        entry.put(AirportCacheUtils.LAND_AREA, getLandArea());
        entry.put(AirportCacheUtils.LATITUDE, getGeographicPosition().getLatitude());
        entry.put(AirportCacheUtils.LONGITUDE, getGeographicPosition().getLongitude());
        entry.put(AirportCacheUtils.OWNERSHIP, getOwnership());
        entry.put(AirportCacheUtils.FACILITY_TYPE, getFacilityType());
        entry.put(AirportCacheUtils.BASED_SINGLE_ENGINE_AIRCRAFT, getBasedSingleEngineAircraft());
        entry.put(AirportCacheUtils.BASED_MULTI_ENGINE_AIRCRAFT, getBasedMultiEngineAircraft());
        entry.put(AirportCacheUtils.BASED_JET_ENGINE_AIRCRAFT, getBasedJetEngineAircraft());
        entry.put(AirportCacheUtils.BASED_JET_ENGINE_AIRCRAFT, getBasedJetEngineAircraft());
        entry.put(AirportCacheUtils.BASED_HELICOPTERS, getBasedHelicopters());
        entry.put(AirportCacheUtils.BASED_GLIDERS, getBasedGliders());
        entry.put(AirportCacheUtils.BASED_MILITARY_AIRCRAFT, getBasedMilitaryAircraft());
        entry.put(AirportCacheUtils.BASED_ULTRALIGHT_AIRCRAFT, getBasedUltralightAircraft());

        StringBuilder runways = new StringBuilder();
        for (int i = 0; i < runwayLinks.size(); i++) {

            runways.append(runwayLinks.get(i).key().toString());

            if (i < runwayLinks.size() - 1) {

                runways.append(",");
            }
        }
        entry.put(AirportCacheUtils.RUNWAYS, runways.toString());
    }
}
