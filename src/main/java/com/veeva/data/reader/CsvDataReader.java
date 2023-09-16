package com.veeva.data.reader;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvDataReader implements DataReader {
    /**
     * Reads data from a CSV file and returns it as a list of maps.
     *
     * @param filePath The path to the CSV file.
     * @return A list of maps representing the CSV data.
     */
    @Override
    public List<Map<String, String>> readData(String filePath) {
        // store all rows (using map for column name: value) of a csv in a list
        List<Map<String, String>> dataList = new ArrayList<>();

        try (FileReader fileReader = new FileReader(filePath);
                CSVReader csvReader = new CSVReader(fileReader)) {
            // read the csv header (column names)
            String[] header = csvReader.readNext();

            if (header != null) {
                String[] nextLine;
                while ((nextLine = csvReader.readNext()) != null) {
                    boolean isEmptyRow = true;

                    for (String value : nextLine) {
                        if (!value.isEmpty()) {
                            isEmptyRow = false;
                            break; // exit the loop as soon as a non-empty value is found
                        }
                    }

                    // if the row is not empty, create a data record (Map) for it
                    if (!isEmptyRow) {
                        Map<String, String> dataRecord = new HashMap<>();

                        for (int i = 0; i < header.length; i++) {
                            String columnName = header[i].toLowerCase();
                            String columnValue = (i < nextLine.length) ? nextLine[i] : "";
                            dataRecord.put(columnName, columnValue);
                        }

                        // add row to the list
                        dataList.add(dataRecord);
                    }
                }
            } else {
                throw new IllegalStateException("CSV file is empty.");
            }
        } catch (IOException e) {
            throw new IllegalStateException("Error reading CSV file", e);
        }

        return dataList;
    }
}
