package com.veeva.data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.*;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DataWriterTest {

    @Test
    void testWriteToOutputFileWithData(@TempDir Path tempDir) throws IOException {
        String fileName = tempDir.resolve("test.csv").toString();

        List<Map<String, String>> dataList = new ArrayList<>();
        Map<String, String> dataRow = new LinkedHashMap<>();
        dataRow.put("id", "1");
        dataRow.put("name", "John");
        dataList.add(dataRow);

        DataWriter.writeToOutputFile(dataList, fileName);

        // verify that the file has been created and contains the expected data
        File outputFile = new File(fileName);
        assertTrue(outputFile.exists());

        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            String line;
            List<String> fileContent = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }

            assertEquals("id,name", fileContent.get(0));
            assertEquals("1,John", fileContent.get(1));
        }
    }

    @Test
    void testWriteToOutputFileWithEmptyData(@TempDir Path tempDir) {
        String fileName = tempDir.resolve("test.csv").toString();

        List<Map<String, String>> dataList = new ArrayList<>();

        DataWriter.writeToOutputFile(dataList, fileName);

        // verify that the file has not been created
        File outputFile = new File(fileName);
        assertFalse(outputFile.exists());
    }
}
