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
import com.github.chrisblutz.jetway.caching.Cache;
import com.github.chrisblutz.jetway.exceptions.JetwayException;
import gov.faa.aixm51.SubscriberFileComponentPropertyType;
import gov.faa.aixm51.SubscriberFileDocument;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xmlbeans.XmlException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class contains the general entry-point methods for dealing
 * with NASR files and AIXM data.
 *
 * @author Christopher Lutz
 */
public final class AIXM {

    private static final File TEMP_DIRECTORY = new File(".jetway/temp");
    private static final String AIXM_DIRECTORY_LOCATION = "Additional_Data/AIXM/AIXM_5.1/XML-Subscriber-Files/";
    private static final String APT_ZIP = "APT_AIXM.zip";
    private static final String APT_XML = "APT_AIXM.xml";
    private static final Pattern ID_PATTERN = Pattern.compile("([a-zA-Z0-9_]+)\\[id=([a-zA-Z0-9_]+)]");

    private static List<File> temporaryFiles = new ArrayList<>();
    private static Map<AIXMType, Class<? extends AIXMLoadable>> types = new HashMap<>();
    private static Map<AIXMType, Boolean> clearTypes = new HashMap<>();
    private static Map<Class<? extends AIXMLoadable>, Cache> caches = new HashMap<>();

    private static Map<Class<? extends AIXMLoadable>, AIXMLoadable> existingInstances = new HashMap<>();

    private static Logger logger = null;

    private AIXM() {

    }

    /**
     * Builds Jetway's cache information from the source NASR file
     * specified by {@link Jetway#stageNASRFile(File)}.  This method
     * also empties any loaded objects from the caches at the
     * completion of loading.  This ensures that no extraneous AIXM
     * features remain loaded; only persistent data remains in the
     * cache.
     *
     * @throws JetwayException if an error occurs while reading the AIXM
     *                         data or building the cache information.
     */
    public static void buildCache() throws JetwayException {

        getLogger().info("Checking NASR file validity...");
        checkNASRLocation();

        getLogger().info("Defining default AIXM-Jetway type converters...");
        Converters.defineEnumConverters();

        getLogger().info("Defining default AIXM types...");
        Types.defineTypes();

        getLogger().info("Loading airport/heliport facility data...");
        loadAirports();

        getLogger().info("Removing temporary files...");
        if (!removeTemporaryFiles()) {

            getLogger().warn("Failed to remove temporary files.");
        }

        getLogger().info("Emptying airport cache to files...");
        Jetway.getAirportCache().emptyAll();
    }

    static void registerType(AIXMType type, Class<? extends AIXMLoadable> loadableType, Cache cache, boolean clear) {

        types.put(type, loadableType);
        clearTypes.put(type, clear);
        caches.put(loadableType, cache);
    }

    private static void checkNASRLocation() throws JetwayException {

        if (Jetway.getNASRFileLocation() == null) {

            getLogger().error("NASR file cannot be null.");
            throw new JetwayException("NASR data file cannot be null.");

        } else if (!Jetway.getNASRFileLocation().exists()) {

            getLogger().error("NASR file does not exist.");
            throw new JetwayException("NASR file does not exist.", new FileNotFoundException(Jetway.getNASRFileLocation().getAbsolutePath()));
        }
    }

    private static void loadAirports() throws JetwayException {

        getLogger().info("Copying AIXM file into temporary directory...");
        File apt = getAirportFile();

        try {

            getLogger().info("Reading AIXM file into memory using XMLBeans...");
            SubscriberFileDocument doc = SubscriberFileDocument.Factory.parse(apt);

            getLogger().info("Reading " + doc.getSubscriberFile().getMemberArray().length + " AIXM members from loaded file...");
            loadAirportMembers(doc.getSubscriberFile().getMemberArray());

        } catch (IOException | XmlException e) {

            getLogger().error("Failed to load AIXM file.", e);
            throw new JetwayException("Failed to load AIXM file.", e);
        }
    }

    private static File getAirportFile() throws JetwayException {

        if (!TEMP_DIRECTORY.mkdirs()) {

            getLogger().error("Failed to create Jetway's temporary directory. (" + TEMP_DIRECTORY.getPath() + ")");
            throw new JetwayException("Failed to create Jetway's temporary directory. (" + TEMP_DIRECTORY.getAbsolutePath() + ")");
        }

        try {

            File zipTemp = new File(TEMP_DIRECTORY, APT_ZIP);
            File xmlTemp = new File(TEMP_DIRECTORY, APT_XML);
            temporaryFiles.add(zipTemp);
            temporaryFiles.add(xmlTemp);

            getLogger().info("Loading NASR file...");
            try (FileSystem outerZip = FileSystems.newFileSystem(Jetway.getNASRFileLocation().toPath(), null)) {

                getLogger().info("Copying inner airport file into temporary directory...");
                Path innerZipPath = outerZip.getPath("/" + AIXM_DIRECTORY_LOCATION + APT_ZIP);
                Files.copy(innerZipPath, zipTemp.toPath(), StandardCopyOption.REPLACE_EXISTING);

                getLogger().info("Loading temporary airport file...");
                try (FileSystem innerZip = FileSystems.newFileSystem(zipTemp.toPath(), null)) {

                    getLogger().info("Copying inner airport AIXM XML file into temporary directory...");
                    Path xmlPath = innerZip.getPath("/" + APT_XML);
                    Files.copy(xmlPath, xmlTemp.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }

            return xmlTemp;

        } catch (IOException e) {

            getLogger().error("Failed to extract AIXM file from zipped NASR file.", e);
            throw new JetwayException("Failed to extract AIXM file from zipped NASR file.", e);
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadAirportMembers(SubscriberFileComponentPropertyType[] members) throws JetwayException {

        existingInstances = new HashMap<>();

        for (SubscriberFileComponentPropertyType member : members) {

            for (AIXMType type : types.keySet()) {

                try {

                    String aixmType = type.getIdentifier();
                    String aixmId = null;
                    Matcher m = ID_PATTERN.matcher(aixmType);
                    if (m.matches()) {

                        aixmType = m.group(1);
                        aixmId = m.group(2);
                    }

                    Object aixmObject = SubscriberFileComponentPropertyType.class.getMethod("get" + aixmType).invoke(member);
                    if (aixmObject != null) {

                        if (aixmId != null) {

                            Object aixmObjectId = aixmObject.getClass().getMethod("getId").invoke(aixmObject);
                            if (aixmObjectId != null && !aixmObjectId.toString().matches(aixmId + "_[0-9_]+")) {

                                continue;
                            }
                        }

                        Pair<AIXMLoadable, Boolean> result = getInstance(type, clearTypes.get(type));
                        AIXMLoadable loadable = result.getKey();
                        AIXMFeature timeSlice = getTimeSlice(aixmObject, aixmType);

                        loadable.loadFromAIXM(type, timeSlice);

                        UUID uuid = UUID.randomUUID();
                        if (result.getValue() && caches.get(types.get(type)) != null) {

                            try {

                                caches.get(types.get(type)).add(uuid, loadable);

                            } catch (ClassCastException e) {

                                getLogger().error("Cache specified for type " + types.get(type).getName() + " did not accept object of type " + loadable.getClass().getName() + ".", e);
                                throw new JetwayException("Cache specified for type " + types.get(type).getName() + " did not accept object of type " + loadable.getClass().getName() + ".");
                            }
                        }

                        if (type.getAssignmentClass() != null && existingInstances.containsKey(type.getAssignmentClass())) {

                            ((AIXMAssignable) existingInstances.get(type.getAssignmentClass())).assign(uuid, loadable);
                        }
                    }

                } catch (Exception e) {

                    getLogger().error("Failed to load AIXM member.", e);
                    throw new JetwayException("Failed to load AIXM member.", e);
                }
            }
        }
    }

    private static Pair<AIXMLoadable, Boolean> getInstance(AIXMType type, boolean clear) throws JetwayException {

        try {

            boolean newObj = false;
            Class<? extends AIXMLoadable> typeClass = types.get(type);
            if (clear || !existingInstances.containsKey(typeClass)) {

                existingInstances.remove(typeClass);
                AIXMLoadable aixmLoadable = typeClass.newInstance();
                existingInstances.put(typeClass, aixmLoadable);
                newObj = true;
            }

            return new Pair<>(existingInstances.get(typeClass), newObj);

        } catch (Exception e) {

            getLogger().error("Could not create instance of type " + types.get(type).getName() + ".", e);
            throw new JetwayException("Could not create instance of type " + types.get(type).getName() + ".", e);
        }
    }

    private static AIXMFeature getTimeSlice(Object aixmObject, String aixmType) throws JetwayException {

        try {

            Object timeSliceAbstract = ((Object[]) aixmObject.getClass().getMethod("getTimeSliceArray").invoke(aixmObject))[0];
            Object timeSlice = timeSliceAbstract.getClass().getMethod("get" + aixmType + "TimeSlice").invoke(timeSliceAbstract);
            return new AIXMFeature(timeSlice, aixmType);

        } catch (Exception e) {

            getLogger().error("Failed to extract time slice for AIXM member.", e);
            throw new JetwayException("Failed to extract time slice for AIXM member.", e);
        }
    }

    private static boolean removeTemporaryFiles() {

        boolean success = true;
        for (File file : temporaryFiles) {

            if (!file.delete()) {

                success = false;
            }
        }

        return success && TEMP_DIRECTORY.delete();
    }

    static Logger getLogger() {

        if (logger == null) {

            logger = LogManager.getLogger("AIXM Loader");
        }

        return logger;
    }
}
