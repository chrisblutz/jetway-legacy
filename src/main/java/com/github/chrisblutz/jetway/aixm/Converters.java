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

import aero.aixm.v5.CodeAirportHeliportType;
import aero.aixm.v5.CodeYesNoType;
import aero.aixm.v5.impl.CodeYesNoTypeImpl;
import com.github.chrisblutz.jetway.aixm.converters.DefaultAIXMConverter;
import com.github.chrisblutz.jetway.features.airports.FacilityType;
import com.github.chrisblutz.jetway.features.airports.Ownership;
import com.github.chrisblutz.jetway.features.positioning.GeoCoordinate;
import gov.faa.aixm51.apt.AirportHeliportExtensionType;
import net.opengis.gml.x32.DirectPositionType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class contains Jetway's default {@link AIXMConverter} instances, as well
 * as the means to register {@code enum} converters.
 *
 * @author Christopher Lutz
 */
public final class Converters {

    /**
     * This {@link AIXMConverter} instance converts AIXM data into {@code boolean} values.
     */
    public static final AIXMConverter<Boolean> BOOLEAN_CONVERTER = new AIXMConverter<Boolean>() {

        @Override
        public Boolean convert(Object value) {

            if (value instanceof CodeYesNoType) {

                return ((CodeYesNoType) value).getObjectValue() == CodeYesNoTypeImpl.YES;

            } else {

                return false;
            }
        }

        @Override
        public Class<?>[] acceptedTypes() {

            return new Class<?>[]{CodeYesNoType.class};
        }
    };

    /**
     * This {@link AIXMConverter} instance converts AIXM data into {@link GeoCoordinate} values.
     */
    public static final AIXMConverter<GeoCoordinate> GEO_COORDINATE_CONVERTER = new AIXMConverter<GeoCoordinate>() {

        @Override
        public GeoCoordinate convert(Object value) {

            if (value instanceof DirectPositionType) {

                List values = ((DirectPositionType) value).getListValue();
                double latitude = (double) values.get(1);
                double longitude = (double) values.get(0);

                return new GeoCoordinate(latitude, longitude);

            } else {

                return null;
            }
        }

        @Override
        public Class<?>[] acceptedTypes() {

            return new Class<?>[]{DirectPositionType.class};
        }
    };

    /**
     * This {@link AIXMConverter} instance converts AIXM data into {@code byte} values.
     */
    public static final AIXMConverter<Byte> BYTE_CONVERTER = new DefaultAIXMConverter<>((byte) 0, Byte::parseByte);

    /**
     * This {@link AIXMConverter} instance converts AIXM data into {@code char} values.
     */
    public static final AIXMConverter<Character> CHARACTER_CONVERTER = new DefaultAIXMConverter<>('\0', string -> string.length() == 1 ? string.charAt(0) : '\0');

    /**
     * This {@link AIXMConverter} instance converts AIXM data into {@code double} values.
     */
    public static final AIXMConverter<Double> DOUBLE_CONVERTER = new DefaultAIXMConverter<>(0d, Double::parseDouble);

    /**
     * This {@link AIXMConverter} instance converts AIXM data into {@code float} values.
     */
    public static final AIXMConverter<Float> FLOAT_CONVERTER = new DefaultAIXMConverter<>(0f, Float::parseFloat);

    /**
     * This {@link AIXMConverter} instance converts AIXM data into {@code int} values.
     */
    public static final AIXMConverter<Integer> INTEGER_CONVERTER = new DefaultAIXMConverter<>(0, Integer::parseInt);

    /**
     * This {@link AIXMConverter} instance converts AIXM data into {@code long} values.
     */
    public static final AIXMConverter<Long> LONG_CONVERTER = new DefaultAIXMConverter<>(0L, Long::parseLong);

    /**
     * This {@link AIXMConverter} instance converts AIXM data into {@link String} values.
     */
    public static final AIXMConverter<String> STRING_CONVERTER = new DefaultAIXMConverter<>(null, string -> string);

    /**
     * This {@link AIXMConverter} instance converts AIXM data into {@code short} values.
     */
    public static final AIXMConverter<Short> SHORT_CONVERTER = new DefaultAIXMConverter<>((short) 0, Short::parseShort);

    private static Map<Class<?>, AIXMConverter<?>> enumConverters = new HashMap<>();

    private Converters() {

    }

    /**
     * Retrieves the {@link AIXMConverter} assigned to the specified {@code enum} type,
     * or {@code null} if one does not exist.
     *
     * @param type the class of the {@code enum} type
     * @param <T>  the {@code enum} type
     * @return The {@link AIXMConverter} associated with the specified {@code enum} type
     */
    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> AIXMConverter<T> forEnum(Class<T> type) {

        return (AIXMConverter<T>) enumConverters.get(type);
    }

    /**
     * Registers an {@link AIXMConverter} for the specified {@code enum} type.
     *
     * @param type      the class of the {@code enum} type
     * @param converter the {@link AIXMConverter} for the {@code enum} type
     * @param <T>       the {@code enum} type
     */
    public static <T extends Enum<T>> void registerEnumConverter(Class<T> type, AIXMConverter<T> converter) {

        enumConverters.put(type, converter);
    }

    /**
     * This method defines all of Jetway's default {@code enum} converters.
     * <p>
     * These {@code enum} types are: {@link FacilityType}, {@link Ownership}
     */
    public static void defineEnumConverters() {

        registerEnumConverter(FacilityType.class, new AIXMConverter<FacilityType>() {

            @Override
            public FacilityType convert(Object value) {

                if (value instanceof CodeAirportHeliportType) {

                    return FacilityType.fromAIXM(((CodeAirportHeliportType) value).getStringValue());

                } else {

                    return null;
                }
            }

            @Override
            public Class<?>[] acceptedTypes() {

                return new Class<?>[]{CodeAirportHeliportType.class};
            }
        });

        registerEnumConverter(Ownership.class, new AIXMConverter<Ownership>() {

            @Override
            public Ownership convert(Object value) {

                if (value instanceof AirportHeliportExtensionType.OwnershipType.Enum) {

                    return Ownership.fromAIXM((AirportHeliportExtensionType.OwnershipType.Enum) value);

                } else {

                    return null;
                }
            }

            @Override
            public Class<?>[] acceptedTypes() {

                return new Class<?>[]{AirportHeliportExtensionType.OwnershipType.Enum.class};
            }
        });
    }
}
