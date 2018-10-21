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

package com.github.chrisblutz.jetway.aixm;

import com.github.chrisblutz.jetway.Jetway;
import com.github.chrisblutz.jetway.features.Airport;
import com.github.chrisblutz.jetway.features.Runway;
import com.github.chrisblutz.jetway.features.RunwayEnd;

/**
 * This class contains Jetway's default {@link AIXMType} instances.
 *
 * @author Christopher Lutz
 */
public final class Types {

    /**
     * This {@link AIXMType} corresponds to airports and heliports
     */
    public static final AIXMType AIRPORT_TYPE = AIXMType.forIdentifier("AirportHeliport");
    /**
     * This {@link AIXMType} corresponds to general runway information
     */
    public static final AIXMType RUNWAY_TYPE = AIXMType.forIdentifier("Runway[id=RWY]", Airport.class);
    /**
     * This {@link AIXMType} corresponds to the base end of runways
     */
    public static final AIXMType RUNWAY_BASE_END_TYPE = AIXMType.forIdentifier("Runway[id=RWY_BASE_END]", Runway.class);
    /**
     * This {@link AIXMType} corresponds to the reciprocal end of runways
     */
    public static final AIXMType RUNWAY_RECIPROCAL_END_TYPE = AIXMType.forIdentifier("Runway[id=RWY_RECIPROCAL_END]", Runway.class);
    private Types() {

    }

    /**
     * This method defines the default {@link AIXMType} instances used by Jetway.
     */
    public static void defineTypes() {

        AIXM.registerType(AIRPORT_TYPE, Airport.class, Jetway.getAirportCache(), true);
        AIXM.registerType(RUNWAY_TYPE, Runway.class, Jetway.getRunwayCache(), true);
        AIXM.registerType(RUNWAY_BASE_END_TYPE, RunwayEnd.class, null, true);
        AIXM.registerType(RUNWAY_RECIPROCAL_END_TYPE, RunwayEnd.class, null, true);
    }
}
