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

package com.github.chrisblutz.jetway.features.positioning;

/**
 * Represents a geographical coordinate.
 *
 * @author Christopher Lutz
 */
public class GeoCoordinate {

    private double latitude, longitude;

    /**
     * Creates a new {@code GeoCoordinate} instance for
     * the specified latitude and longitude.
     *
     * @param latitude  the latitude of the coordinate (degrees)
     * @param longitude the longitude of the coordinate (degrees)
     */
    public GeoCoordinate(double latitude, double longitude) {

        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Retrieves the latitude for this geographic coordinate
     * in degrees.
     *
     * @return the latitude of this coordinate (degrees)
     */
    public double getLatitude() {

        return latitude;
    }

    /**
     * Retrieves the longitude for this geographic coordinate
     * in degrees.
     *
     * @return the longitude of this coordinate (degrees)
     */
    public double getLongitude() {

        return longitude;
    }

    /**
     * Returns this {@code GeoCoordinate} in the form of a {@link String}.
     * The format is {@code GeoCoordinate[latitude=X, longitude=X]}.
     *
     * @return The {@link String} form of this coordinate
     */
    @Override
    public String toString() {

        return getClass().getSimpleName() + "[latitude=" + getLatitude() + ", longitude=" + getLongitude() + "]";
    }
}
