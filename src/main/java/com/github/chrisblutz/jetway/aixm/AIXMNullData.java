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

/**
 * This is a singleton class that represents an {@link AIXMData}
 * instance with a {@code null} underlying value.  This prevents
 * {@link NullPointerException}s.  Every value retrieval attempt
 * on the instance of {@code AIXMNullData} will return {@code null}.
 *
 * @author Christopher Lutz
 */
public class AIXMNullData extends AIXMData {

    private static AIXMNullData instance = null;

    private AIXMNullData() {

        super(null);
    }

    /**
     * Retrieves the singleton instance of {@code AIXMNullData}, initializing it
     * if required.
     *
     * @return The singleton instance of {@code AIXMNullData}.
     */
    public static AIXMNullData getInstance() {

        if (instance == null) {

            instance = new AIXMNullData();
        }

        return instance;
    }

    /**
     * This method always returns {@code null}.  It overrides
     * {@link AIXMData#get(String, AIXMConverter)} to prevent
     * {@link NullPointerException}s on {@code null} underlying
     * data.  Methods that use {@link AIXMData#checkedCrawl(String)}
     * (which produces this singleton instance if underlying data
     * is {@code null}) are responsible for correctly handling or
     * ignoring {@code null} values produced by this method.
     *
     * @param path      the AIXM path to crawl to before extracting the value.
     *                  This has no effect in this implementation of the method.
     * @param converter the converter to use.  This has no effect in this
     *                  implementation of the method.
     * @param <T>       the type expected
     * @return {@code null}
     * @see AIXMData#get(String, AIXMConverter)
     */
    @Override
    public <T> T get(String path, AIXMConverter<T> converter) {

        return null;
    }

    /**
     * This method always returns the singleton instance of
     * {@code AIXMNullData}.  It overrides {@link AIXMData#crawl(String)}
     * to prevent {@link NullPointerException}s on {@code null} underlying
     * data.
     *
     * @param path the AIXM path to crawl to.  This has no effect in this
     *             implementation of the method.
     * @return The singleton instance of {@code AIXMNullData}.
     */
    @Override
    public AIXMData crawl(String path) {

        return getInstance();
    }
}
