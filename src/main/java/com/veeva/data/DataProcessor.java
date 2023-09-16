package com.veeva.data;

import com.veeva.data.reader.DataReader;
import com.veeva.data.reader.DataReaderFactory;
import com.veeva.validator.FileValidator;

import com.veeva.exceptions.UnsupportedDataFormatException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.nio.file.Paths;
import java.util.*;

public class DataProcessor {
    private static final Logger logger = LogManager.getLogger();

    public static List<Map<String, String>> processDataFromFiles(List<String> fileNames, String dataDir) {
        List<List<Map<String, String>>> dataList = new ArrayList<>();

        for (String fileName : fileNames) {
            processDataFromFile(fileName, dataDir, dataList);
        }

        List<Map<String, String>> flattenedData = new ArrayList<>();
        for (List<Map<String, String>> innerList : dataList) {
            flattenedData.addAll(innerList);
        }

        Map<String, Map<String, String>> consolidatedData = consolidateData(flattenedData);

        return DataSorter.sortByID(new ArrayList<>(consolidatedData.values()));
    }

    private static void processDataFromFile(String fileName, String dataDir, List<List<Map<String, String>>> dataList) {
        try {
            String filePath = Paths.get(dataDir, fileName).toString();

            // validate user provided file path and file size
            String validatedFilePath = FileValidator.validateFile(filePath);

            // if valid filepath is provided, get DataReader based on file type
            DataReader dataReader = DataReaderFactory.createDataReader(validatedFilePath);

            // read data from html or csv file
            List<Map<String, String>> data = dataReader.readData(validatedFilePath);
            dataList.add(data);

            // since there is no UI, show results on console, also write to log
            String message = "File " + fileName +" was read successfully";
            logger.info(message);
            System.out.println(message);
        } catch (IllegalArgumentException | IllegalStateException | SecurityException ex) {
            handleException(fileName, ex);
        } catch (UnsupportedDataFormatException ex) {
            System.out.println("File: " + fileName + ", Issue: " + ex.getMessage());
        } catch (Exception ex) {
            logger.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        }
    }

    private static void handleException(String fileName, Exception ex) {
        // log and print exception messages
        String message = "File: " + fileName + ", Issue: " + ex.getMessage();
        logger.info(message);
        System.out.println(message);
    }

    protected static Map<String, Map<String, String>> consolidateData(
            List<Map<String, String>> data) {
        Map<String, Map<String, String>> consolidatedData = new HashMap<>();
        Map<String, Map<String, String>> nullIdMaps = new HashMap<>();

        for (Map<String, String> map : data) {
            String id = map.get("id");

            if (id == null) {
                // if id is null, add the map directly to consolidatedData
                nullIdMaps.put(UUID.randomUUID().toString(), map);
            } else {
                consolidatedData.merge(id, map, (existingMap, newMap) -> {
                    existingMap.putAll(newMap);
                    return existingMap;
                });
            }
        }

        consolidatedData.putAll(nullIdMaps);

        return consolidatedData;
    }
}
