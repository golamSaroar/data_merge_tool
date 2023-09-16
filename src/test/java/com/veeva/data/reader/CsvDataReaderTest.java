package com.veeva.data.reader;

import com.veeva.config.ConfigurationManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CsvDataReaderTest {

    private static String dataDir;
    private CsvDataReader csvDataReader;

    @BeforeAll static void setUp() {
        System.setProperty("active.profile", "qa");
        ConfigurationManager configManager = ConfigurationManager.getInstance();
        dataDir = configManager.getProperty("app.data.directory", "data/test");
    }

    @BeforeEach public void init() {
        csvDataReader = new CsvDataReader();
    }

    @Test void testReadDataWithValidCsv() {
        String filePath = Paths.get(dataDir, "valid.csv").toString();
        List<Map<String, String>> dataList = csvDataReader.readData(filePath);

        assertNotNull(dataList);
        assertFalse(dataList.isEmpty());

        Map<String, String> firstRow = dataList.get(0);
        assertEquals("Pilot", firstRow.get("occupation"));
        assertEquals("Jerry Springfield", firstRow.get("name"));
    }

    @Test void testReadDataWithEmptyCsv() {
        String filePath = Paths.get(dataDir, "empty.csv").toString();
        IllegalStateException exception =
                assertThrows(IllegalStateException.class, () -> csvDataReader.readData(filePath));

        assertEquals("CSV file is empty.", exception.getMessage());
    }
}

