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

/**
 * Represents a base AIXM feature object (i.e.
 * airport, runway, etc.)
 *
 * @author Christopher Lutz
 */
public class AIXMFeature extends AIXMData {

    private String type;
    private AIXMData extension = null;

    /**
     * Creates a new feature instance with the specified
     * underlying data and the specified feature type.
     *
     * @param data the underlying data for this feature
     * @param type the type of this feature (this will
     *             usually be the identifier returned by
     *             {@link AIXMType#getIdentifier()} on a
     *             specific {@link AIXMType})
     */
    public AIXMFeature(Object data, String type) {

        super(data);

        this.type = type;
    }

    /**
     * Gets the AIXM extension associated with this feature.
     *
     * @return The {@link AIXMData} representing
     * the extension associated with this feature
     * @throws AIXMDataException if the extension cannot be retrieved
     */
    public AIXMData extension() {

        return extension(false);
    }

    /**
     * Gets the AIXM extension associated with this feature, allowing
     * {@code null} if the extension cannot be retrieved and
     * {@code allowNull} is {@code true}.
     *
     * @return The {@link AIXMData} representing
     * the extension associated with this feature
     * @throws AIXMDataException if the extension cannot be retrieved and
     *                           {@code null} values are not allowed.
     */
    public AIXMData extension(boolean allowNull) {

        if (extension == null) {

            try {

                Object extensionAbstract = ((Object[]) data.getClass().getMethod("getExtensionArray").invoke(data))[0];
                extension = new AIXMData(extensionAbstract.getClass().getMethod("getAbstract" + type + "Extension").invoke(extensionAbstract));

            } catch (Exception e) {

                extension = AIXMNullData.getInstance();
                if (allowNull) {

                    return extension;

                } else {

                    AIXM.getLogger().error("Could not load AIXM extension for feature of type " + type + ".", e);
                    throw new AIXMDataException("Could not load AIXM extension for feature of type " + type + ".");
                }
            }
        }

        return extension;
    }
}
