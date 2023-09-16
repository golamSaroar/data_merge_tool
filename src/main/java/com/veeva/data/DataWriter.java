package com.veeva.data;

import com.veeva.config.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DataWriter {
    private static final ConfigurationManager configManager = ConfigurationManager.getInstance();
    private static final Logger logger = LogManager.getLogger();

    public static void writeToOutputFile(List<Map<String, String>> dataList, String outputFilePath) {
        if (dataList.isEmpty()) {
            String message = "No data to save to " + outputFilePath;
            logger.info(message);
            System.out.println(message);
            return;
        }

        try (FileWriter csvWriter = new FileWriter(outputFilePath)) {
            List<String> header = extractHeader(dataList);
            writeCSVRow(csvWriter, header);

            for (Map<String, String> dataRow : dataList) {
                List<String> rowValues = header.stream().map(key -> dataRow.getOrDefault(key, ""))
                        .collect(Collectors.toList());
                writeCSVRow(csvWriter, rowValues);
            }
            String message = "Data has been written to " + outputFilePath;
            logger.info(message);
            System.out.println(message);
        } catch (IOException e) {
            logger.error("Error writing to " + outputFilePath, e);
        }
    }

    private static List<String> extractHeader(List<Map<String, String>> dataList) {
        Set<String> headerSet = new LinkedHashSet<>();
        for (Map<String, String> dataRow : dataList) {
            headerSet.addAll(dataRow.keySet());
        }
        List<String> header = new ArrayList<>(headerSet);
        header.remove("id");
        header.add(0, "id");
        return header;
    }

    private static void writeCSVRow(FileWriter csvWriter, List<String> values) throws IOException {
        StringJoiner row = new StringJoiner(configManager.getProperty("csv.delimiter"));
        values.forEach(value -> row.add("\"" + value + "\""));
        csvWriter.append(row.toString()).append("\n");
    }
}
