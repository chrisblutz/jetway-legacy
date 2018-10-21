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

import com.github.chrisblutz.jetway.exceptions.AIXMDataException;
import com.github.chrisblutz.jetway.features.positioning.GeoCoordinate;

/**
 * Represents a generic piece of raw AIXM feature data.
 *
 * @author Christopher Lutz
 */
public class AIXMData {

    protected Object data;

    /**
     * Creates a new {@code AIXMData} instance with
     * the specified underlying object.
     *
     * @param data the AIXM data object
     */
    public AIXMData(Object data) {

        this.data = data;
    }

    /**
     * Extracts a {@code boolean} value from this {@code AIXMData}
     * instance.
     * <p>
     * This method is equivalent to calling
     * <pre>
     *     getBoolean(null);
     * </pre>
     *
     * @return A {@code boolean} value corresponding to the value
     * contained in this {@code AIXMData} instance.  If the underlying
     * value is not a convertible type, an {@link AIXMDataException} will
     * be thrown.
     * @throws AIXMDataException if the current {@code AIXMData} instance
     *                           cannot be converted into a {@code boolean}.
     * @see AIXMData#getBoolean(String)
     * @see AIXMData#get(AIXMConverter)
     * @see AIXMData#get(String, AIXMConverter)
     */
    public boolean getBoolean() {

        return getBoolean(null);
    }

    /**
     * Crawls to the specified path and extracts a {@code boolean} value
     * from the resulting {@code AIXMData} instance.
     *
     * @param path the AIXM path to crawl to before extracting the value
     * @return A {@code boolean} value corresponding to the value
     * contained in the {@code AIXMData} instance indicated by the specified
     * path.  If the underlying value is not a convertible type, an
     * {@link AIXMDataException} will be thrown.
     * @throws AIXMDataException if the resulting {@code AIXMData} instance
     *                           cannot be converted into a {@code boolean}.
     * @see AIXMData#get(String, AIXMConverter)
     */
    public boolean getBoolean(String path) {

        return get(path, Converters.BOOLEAN_CONVERTER);
    }

    /**
     * Extracts a {@code byte} value from this {@code AIXMData}
     * instance.
     * <p>
     * This method is equivalent to calling
     * <pre>
     *     getByte(null);
     * </pre>
     *
     * @return A {@code byte} corresponding to the value
     * contained in this {@code AIXMData} instance.  If the underlying
     * value is not a convertible type, an {@link AIXMDataException} will
     * be thrown.
     * @throws AIXMDataException if the current {@code AIXMData} instance
     *                           cannot be converted into a {@code byte}.
     * @see AIXMData#getByte(String)
     * @see AIXMData#get(AIXMConverter)
     * @see AIXMData#get(String, AIXMConverter)
     */
    public byte getByte() {

        return getByte(null);
    }

    /**
     * Crawls to the specified path and extracts a {@code byte} value
     * from the resulting {@code AIXMData} instance.
     *
     * @param path the AIXM path to crawl to before extracting the value
     * @return A {@code byte} corresponding to the value
     * contained in the {@code AIXMData} instance indicated by the specified
     * path.  If the underlying value is not a convertible type, an
     * {@link AIXMDataException} will be thrown.
     * @throws AIXMDataException if the resulting {@code AIXMData} instance
     *                           cannot be converted into a {@code byte}.
     * @see AIXMData#get(String, AIXMConverter)
     */
    public byte getByte(String path) {

        return get(path, Converters.BYTE_CONVERTER);
    }

    /**
     * Extracts a {@code char} value from this {@code AIXMData}
     * instance.
     * <p>
     * This method is equivalent to calling
     * <pre>
     *     getCharacter(null);
     * </pre>
     *
     * @return A {@code char} corresponding to the value
     * contained in this {@code AIXMData} instance.  If the underlying
     * value is not a convertible type, an {@link AIXMDataException} will
     * be thrown.
     * @throws AIXMDataException if the current {@code AIXMData} instance
     *                           cannot be converted into a {@code char}.
     * @see AIXMData#getCharacter(String)
     * @see AIXMData#get(AIXMConverter)
     * @see AIXMData#get(String, AIXMConverter)
     */
    public char getCharacter() {

        return getCharacter(null);
    }

    /**
     * Crawls to the specified path and extracts a {@code char} value
     * from the resulting {@code AIXMData} instance.
     *
     * @param path the AIXM path to crawl to before extracting the value
     * @return A {@code char} corresponding to the value
     * contained in the {@code AIXMData} instance indicated by the specified
     * path.  If the underlying value is not a convertible type, an
     * {@link AIXMDataException} will be thrown.
     * @throws AIXMDataException if the resulting {@code AIXMData} instance
     *                           cannot be converted into a {@code char}.
     * @see AIXMData#get(String, AIXMConverter)
     */
    public char getCharacter(String path) {

        return get(path, Converters.CHARACTER_CONVERTER);
    }

    /**
     * Extracts a {@code double} value from this {@code AIXMData}
     * instance.
     * <p>
     * This method is equivalent to calling
     * <pre>
     *     getDouble(null);
     * </pre>
     *
     * @return A {@code double} corresponding to the value
     * contained in this {@code AIXMData} instance.  If the underlying
     * value is not a convertible type, an {@link AIXMDataException} will
     * be thrown.
     * @throws AIXMDataException if the current {@code AIXMData} instance
     *                           cannot be converted into a {@code double}.
     * @see AIXMData#getDouble(String)
     * @see AIXMData#get(AIXMConverter)
     * @see AIXMData#get(String, AIXMConverter)
     */
    public double getDouble() {

        return getDouble(null);
    }

    /**
     * Crawls to the specified path and extracts a {@code double} value
     * from the resulting {@code AIXMData} instance.
     *
     * @param path the AIXM path to crawl to before extracting the value
     * @return A {@code double} corresponding to the value
     * contained in the {@code AIXMData} instance indicated by the specified
     * path.  If the underlying value is not a convertible type, an
     * {@link AIXMDataException} will be thrown.
     * @throws AIXMDataException if the resulting {@code AIXMData} instance
     *                           cannot be converted into a {@code double}.
     * @see AIXMData#get(String, AIXMConverter)
     */
    public double getDouble(String path) {

        return get(path, Converters.DOUBLE_CONVERTER);
    }

    /**
     * Extracts a specific {@code enum} value from this {@code AIXMData}
     * instance.
     * <p>
     * This method is equivalent to calling
     * <pre>
     *     getEnum(null, type);
     * </pre>
     *
     * @param type the {@code enum} type to search for.  This type must have
     *             a converter defined in {@link Converters}.
     * @param <T>  the {@code enum} type to convert to
     * @return An {@code enum} value corresponding to the value
     * contained in this {@code AIXMData} instance.  If the underlying
     * value is not a convertible type, an {@link AIXMDataException} will
     * be thrown.
     * @throws AIXMDataException if the current {@code AIXMData} instance
     *                           cannot be converted into a specific {@code enum} value.
     * @see AIXMData#getEnum(String, Class)
     * @see AIXMData#get(AIXMConverter)
     * @see AIXMData#get(String, AIXMConverter)
     */
    public <T extends Enum<T>> T getEnum(Class<T> type) {

        return getEnum(null, type);
    }

    /**
     * Crawls to the specified path and extracts a specific {@code enum} value
     * from the resulting {@code AIXMData} instance.
     *
     * @param path the AIXM path to crawl to before extracting the value
     * @param type the {@code enum} type to search for.  This type must have
     *             a converter defined in {@link Converters}.
     * @param <T>  the {@code enum} type to convert to
     * @return An {@code enum} value corresponding to the value
     * contained in the {@code AIXMData} instance indicated by the specified
     * path.  If the underlying value is not a convertible type, an
     * {@link AIXMDataException} will be thrown.
     * @throws AIXMDataException if the resulting {@code AIXMData} instance
     *                           cannot be converted into a specific {@code enum} value.
     * @see AIXMData#get(String, AIXMConverter)
     */
    public <T extends Enum<T>> T getEnum(String path, Class<T> type) {

        return get(path, Converters.forEnum(type));
    }

    /**
     * Extracts a {@code float} value from this {@code AIXMData}
     * instance.
     * <p>
     * This method is equivalent to calling
     * <pre>
     *     getFloat(null);
     * </pre>
     *
     * @return A {@code float} corresponding to the value
     * contained in this {@code AIXMData} instance.  If the underlying
     * value is not a convertible type, an {@link AIXMDataException} will
     * be thrown.
     * @throws AIXMDataException if the current {@code AIXMData} instance
     *                           cannot be converted into a {@code float}.
     * @see AIXMData#getFloat(String)
     * @see AIXMData#get(AIXMConverter)
     * @see AIXMData#get(String, AIXMConverter)
     */
    public float getFloat() {

        return getFloat(null);
    }

    /**
     * Crawls to the specified path and extracts a {@code float} value
     * from the resulting {@code AIXMData} instance.
     *
     * @param path the AIXM path to crawl to before extracting the value
     * @return A {@code float} corresponding to the value
     * contained in the {@code AIXMData} instance indicated by the specified
     * path.  If the underlying value is not a convertible type, an
     * {@link AIXMDataException} will be thrown.
     * @throws AIXMDataException if the resulting {@code AIXMData} instance
     *                           cannot be converted into a {@code float}.
     * @see AIXMData#get(String, AIXMConverter)
     */
    public float getFloat(String path) {

        return get(path, Converters.FLOAT_CONVERTER);
    }

    /**
     * Extracts a {@link GeoCoordinate} from this {@code AIXMData}
     * instance.
     * <p>
     * This method is equivalent to calling
     * <pre>
     *     getGeographicCoordinate(null);
     * </pre>
     *
     * @return A {@link GeoCoordinate} corresponding to the value
     * contained in this {@code AIXMData} instance.  If the underlying
     * value is not a convertible type, an {@link AIXMDataException} will
     * be thrown.
     * @throws AIXMDataException if the current {@code AIXMData} instance
     *                           cannot be converted into a {@link GeoCoordinate}.
     * @see AIXMData#getGeographicCoordinate(String)
     * @see AIXMData#get(AIXMConverter)
     * @see AIXMData#get(String, AIXMConverter)
     */
    public GeoCoordinate getGeographicCoordinate() {

        return getGeographicCoordinate(null);
    }

    /**
     * Crawls to the specified path and extracts a {@link GeoCoordinate}
     * from the resulting {@code AIXMData} instance.
     *
     * @param path the AIXM path to crawl to before extracting the value
     * @return A {@link GeoCoordinate} corresponding to the value
     * contained in the {@code AIXMData} instance indicated by the specified
     * path.  If the underlying value is not a convertible type, an
     * {@link AIXMDataException} will be thrown.
     * @throws AIXMDataException if the resulting {@code AIXMData} instance
     *                           cannot be converted into a {@link GeoCoordinate}.
     * @see AIXMData#get(String, AIXMConverter)
     */
    public GeoCoordinate getGeographicCoordinate(String path) {

        return get(path, Converters.GEO_COORDINATE_CONVERTER);
    }

    /**
     * Extracts a {@code int} value from this {@code AIXMData}
     * instance.
     * <p>
     * This method is equivalent to calling
     * <pre>
     *     getInteger(null);
     * </pre>
     *
     * @return A {@code int} corresponding to the value
     * contained in this {@code AIXMData} instance.  If the underlying
     * value is not a convertible type, an {@link AIXMDataException} will
     * be thrown.
     * @throws AIXMDataException if the current {@code AIXMData} instance
     *                           cannot be converted into a {@code int}.
     * @see AIXMData#getInteger(String)
     * @see AIXMData#get(AIXMConverter)
     * @see AIXMData#get(String, AIXMConverter)
     */
    public int getInteger() {

        return getInteger(null);
    }

    /**
     * Crawls to the specified path and extracts a {@code int} value
     * from the resulting {@code AIXMData} instance.
     *
     * @param path the AIXM path to crawl to before extracting the value
     * @return A {@code int} corresponding to the value
     * contained in the {@code AIXMData} instance indicated by the specified
     * path.  If the underlying value is not a convertible type, an
     * {@link AIXMDataException} will be thrown.
     * @throws AIXMDataException if the resulting {@code AIXMData} instance
     *                           cannot be converted into a {@code int}.
     * @see AIXMData#get(String, AIXMConverter)
     */
    public int getInteger(String path) {

        return get(path, Converters.INTEGER_CONVERTER);
    }

    /**
     * Extracts a {@code long} value from this {@code AIXMData}
     * instance.
     * <p>
     * This method is equivalent to calling
     * <pre>
     *     getLong(null);
     * </pre>
     *
     * @return A {@code long} corresponding to the value
     * contained in this {@code AIXMData} instance.  If the underlying
     * value is not a convertible type, an {@link AIXMDataException} will
     * be thrown.
     * @throws AIXMDataException if the current {@code AIXMData} instance
     *                           cannot be converted into a {@code long}.
     * @see AIXMData#getLong(String)
     * @see AIXMData#get(AIXMConverter)
     * @see AIXMData#get(String, AIXMConverter)
     */
    public long getLong() {

        return getLong(null);
    }

    /**
     * Crawls to the specified path and extracts a {@code long} value
     * from the resulting {@code AIXMData} instance.
     *
     * @param path the AIXM path to crawl to before extracting the value
     * @return A {@code long} corresponding to the value
     * contained in the {@code AIXMData} instance indicated by the specified
     * path.  If the underlying value is not a convertible type, an
     * {@link AIXMDataException} will be thrown.
     * @throws AIXMDataException if the resulting {@code AIXMData} instance
     *                           cannot be converted into a {@code long}.
     * @see AIXMData#get(String, AIXMConverter)
     */
    public long getLong(String path) {

        return get(path, Converters.LONG_CONVERTER);
    }

    /**
     * Extracts a {@code short} value from this {@code AIXMData}
     * instance.
     * <p>
     * This method is equivalent to calling
     * <pre>
     *     getShort(null);
     * </pre>
     *
     * @return A {@code short} corresponding to the value
     * contained in this {@code AIXMData} instance.  If the underlying
     * value is not a convertible type, an {@link AIXMDataException} will
     * be thrown.
     * @throws AIXMDataException if the current {@code AIXMData} instance
     *                           cannot be converted into a {@code short}.
     * @see AIXMData#getShort(String)
     * @see AIXMData#get(AIXMConverter)
     * @see AIXMData#get(String, AIXMConverter)
     */
    public short getShort() {

        return getShort(null);
    }

    /**
     * Crawls to the specified path and extracts a {@code short} value
     * from the resulting {@code AIXMData} instance.
     *
     * @param path the AIXM path to crawl to before extracting the value
     * @return A {@code short} corresponding to the value
     * contained in the {@code AIXMData} instance indicated by the specified
     * path.  If the underlying value is not a convertible type, an
     * {@link AIXMDataException} will be thrown.
     * @throws AIXMDataException if the resulting {@code AIXMData} instance
     *                           cannot be converted into a {@code short}.
     * @see AIXMData#get(String, AIXMConverter)
     */
    public short getShort(String path) {

        return get(path, Converters.SHORT_CONVERTER);
    }

    /**
     * Extracts a {@code String} value from this {@code AIXMData}
     * instance.
     * <p>
     * This method is equivalent to calling
     * <pre>
     *     getString(null);
     * </pre>
     *
     * @return A {@code String} corresponding to the value
     * contained in this {@code AIXMData} instance.  If the underlying
     * value is not a convertible type, an {@link AIXMDataException} will
     * be thrown.
     * @throws AIXMDataException if the current {@code AIXMData} instance
     *                           cannot be converted into a {@code String}.
     * @see AIXMData#getString(String)
     * @see AIXMData#get(AIXMConverter)
     * @see AIXMData#get(String, AIXMConverter)
     */
    public String getString() {

        return getString(null);
    }

    /**
     * Crawls to the specified path and extracts a {@code String} value
     * from the resulting {@code AIXMData} instance.
     *
     * @param path the AIXM path to crawl to before extracting the value
     * @return A {@code String} corresponding to the value
     * contained in the {@code AIXMData} instance indicated by the specified
     * path.  If the underlying value is not a convertible type, an
     * {@link AIXMDataException} will be thrown.
     * @throws AIXMDataException if the resulting {@code AIXMData} instance
     *                           cannot be converted into a {@code String}.
     * @see AIXMData#get(String, AIXMConverter)
     */
    public String getString(String path) {

        return get(path, Converters.STRING_CONVERTER);
    }

    /**
     * Extracts a value from this {@code AIXMData} instance using the
     * specified {@link AIXMConverter}.
     * <p>
     * This method is equivalent to calling
     * <pre>
     *     get(null, converter);
     * </pre>
     *
     * @param converter the converter to use
     * @param <T>       the type to convert to
     * @return A value of type {@code <T>} extracted
     * using the specified {@link AIXMConverter}.  If the underlying
     * value is not a convertible type, an {@link AIXMDataException}
     * will be thrown.
     * @throws AIXMDataException if the current {@code AIXMData} instance cannot be
     *                           converted into the correct type.
     * @see AIXMData#get(String, AIXMConverter)
     */
    public <T> T get(AIXMConverter<T> converter) {

        return get(null, converter);
    }

    /**
     * Crawls to the specified path and extracts a value from the resulting
     * {@code AIXMData} instance using the specified {@link AIXMConverter}.
     *
     * @param path      the AIXM path to crawl to before extracting the value
     * @param converter the converter to use
     * @param <T>       the type to convert to
     * @return A value of type {@code <T>} corresponding to the value
     * contained in the {@code AIXMData} instance indicated by the specified
     * path.  If the underlying value is not a convertible type, an
     * {@link AIXMDataException} will be thrown.
     * @throws AIXMDataException if the resulting {@code AIXMData} instance
     *                           cannot be converted into the correct type.
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String path, AIXMConverter<T> converter) {

        AIXMData data = this;
        if (path != null) {

            data = crawl(path);
        }

        if (data.data != null) {

            boolean matches = false;
            for (Class<?> acceptedType : converter.acceptedTypes()) {

                if (acceptedType.isInstance(data.data)) {

                    matches = true;
                }
            }

            if (!matches) {

                AIXM.getLogger().error("Specified converter does not support elements of type " + (data.data == null ? null : data.data.getClass().getName()) + ".");
                throw new AIXMDataException("Specified converter does not support elements of type " + (data.data == null ? null : data.data.getClass().getName()) + ".");
            }
        }

        return converter.convert(data.data);
    }

    /**
     * Crawls to the specified AIXM path (relative to the current path of
     * this {@code AIXMData} instance) and returns an {@code AIXMData}
     * instance corresponding to that value.  If the path does not exist,
     * {@code null} will be returned.  Use {@link AIXMData#checkedCrawl(String)}
     * if a {@code null} value is occasionally expected.  If an error occurs
     * while retrieving the path, an {@link AIXMDataException} will be thrown.
     *
     * @param path the AIXM path to crawl to
     * @return An {@code AIXMData} instance corresponding to the value at
     * the specified AIXM path.
     * @throws AIXMDataException if crawling the path causes an error.  This
     *                           can happen if a point along the path is {@code null}.  Only the last
     *                           value crawled may be {@code null}.  Use {@link AIXMData#checkedCrawl(String)}
     *                           on the path segment if a {@code null} value is expected along the path.
     * @see AIXMData#checkedCrawl(String)
     */
    public AIXMData crawl(String path) {

        try {

            String subsequentCrawl = null;
            if (path.contains("/") || path.contains("\\")) {

                String[] parts = path.replace('\\', '/').split("/", 2);
                path = parts[0];
                subsequentCrawl = parts[1];
            }

            AIXMData result = new AIXMData(data.getClass().getMethod("get" + path).invoke(data));
            if (subsequentCrawl != null) {

                return result.crawl(subsequentCrawl);

            } else {

                return result;
            }

        } catch (Exception e) {

            AIXM.getLogger().error("Could not retrieve AIXM element for path '" + path + "'.", e);
            throw new AIXMDataException("Could not retrieve AIXM element for path '" + path + "'.");
        }
    }

    /**
     * Crawls to the specified AIXM path (relative to the current path of
     * this {@code AIXMData} instance) and returns an {@code AIXMData}
     * instance corresponding to that value.  If the path does not exist,
     * the {@link AIXMNullData} instance will be returned.  This instance
     * allows further path-crawling, but will <i>always</i> return {@code null}
     * from value retrieval attempts.
     *
     * @param path the AIXM path to crawl to
     * @return An {@code AIXMData} instance corresponding to the value at
     * the specified AIXM path, or the {@link AIXMNullData} instance if
     * the end value is {@code null}.
     * @throws AIXMDataException if crawling the path causes an error.  This
     *                           can happen if a point along the path is {@code null}.  Only the last
     *                           value crawled may be {@code null}.
     */
    public AIXMData checkedCrawl(String path) {

        AIXMData data = crawl(path);

        if (data.data == null) {

            return AIXMNullData.getInstance();

        } else {

            return data;
        }
    }
}
