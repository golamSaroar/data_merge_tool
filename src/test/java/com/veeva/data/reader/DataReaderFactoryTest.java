package com.veeva.data.reader;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.veeva.exceptions.UnsupportedDataFormatException;

public class DataReaderFactoryTest {

    @Test
    void testCreateDataReaderForHtml() throws UnsupportedDataFormatException {
        String filePath = "file.html";
        DataReader dataReader = DataReaderFactory.createDataReader(filePath);

        assertNotNull(dataReader);
        assertTrue(dataReader instanceof HtmlDataReader);
    }

    @Test
    void testCreateDataReaderForCsv() throws UnsupportedDataFormatException {
        String filePath = "file.csv";
        DataReader dataReader = DataReaderFactory.createDataReader(filePath);

        assertNotNull(dataReader);
        assertTrue(dataReader instanceof CsvDataReader);
    }

    @Test
    void testCreateDataReaderForUnsupportedFormat() {
        String filePath = "file.xml"; // Unsupported format

        assertThrows(UnsupportedDataFormatException.class, () -> {
            DataReaderFactory.createDataReader(filePath);
        });
    }
}

