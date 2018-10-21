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

package com.github.chrisblutz.jetway.features.airports;

/**
 * Represents different types of airport facilities.
 *
 * @author Christopher Lutz
 */
public enum FacilityType {

    /**
     * An aerodrome facility
     */
    AERODROME("AD"),
    /**
     * A heliport facility
     */
    HELIPORT("HP"),
    /**
     * A joint aerodrome/heliport facility
     */
    AERODROME_WITH_HELIPORT("AH"),
    /**
     * A landing site
     */
    LANDING_SITE("LS");

    private String aixmData;

    FacilityType(String aixmData) {

        this.aixmData = aixmData;
    }

    /**
     * Retrieves the facility type associated with the
     * specified AIXM string.
     *
     * @param aixm the AIXM string
     * @return The corresponding {@code FacilityType}
     */
    public static FacilityType fromAIXM(String aixm) {

        for (FacilityType value : values()) {

            if (value.aixmData.equals(aixm)) {

                return value;
            }
        }

        return null;
    }
}
