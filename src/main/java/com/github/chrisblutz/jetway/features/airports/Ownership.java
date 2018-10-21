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

import gov.faa.aixm51.apt.AirportHeliportExtensionType;

/**
 * Represents different types of ownership for airports
 * (military, public, private, etc.).
 *
 * @author Christopher Lutz
 */
public enum Ownership {

    /**
     * Public ownership
     */
    PUBLIC,
    /**
     * Private ownership
     */
    PRIVATE,
    /**
     * Air Force ownership
     */
    AIR_FORCE,
    /**
     * Army ownership
     */
    ARMY,
    /**
     * Navy ownership
     */
    NAVY,
    /**
     * Coast Guard ownership
     */
    COAST_GUARD;

    /**
     * Retrieves the ownership type associated with the
     * specified AIXM object.
     *
     * @param aixm the AIXM object
     * @return The corresponding {@code Ownership} type
     */
    public static Ownership fromAIXM(AirportHeliportExtensionType.OwnershipType.Enum aixm) {

        if (aixm == AirportHeliportExtensionType.OwnershipType.PU) {

            return Ownership.PUBLIC;

        } else if (aixm == AirportHeliportExtensionType.OwnershipType.PR) {

            return Ownership.PRIVATE;

        } else if (aixm == AirportHeliportExtensionType.OwnershipType.MA) {

            return Ownership.AIR_FORCE;

        } else if (aixm == AirportHeliportExtensionType.OwnershipType.MR) {

            return Ownership.ARMY;

        } else if (aixm == AirportHeliportExtensionType.OwnershipType.MN) {

            return Ownership.NAVY;

        } else {

            return Ownership.COAST_GUARD;
        }
    }
}
