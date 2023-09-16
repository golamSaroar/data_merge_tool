package com.veeva.data.reader;

import static org.junit.jupiter.api.Assertions.*;

import com.veeva.config.ConfigurationManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class HtmlDataReaderTest {

    private static String dataDir;
    private HtmlDataReader htmlDataReader;

    @BeforeAll
    static void setUp() {
        System.setProperty("active.profile", "qa");
        ConfigurationManager configManager = ConfigurationManager.getInstance();
        dataDir = configManager.getProperty("app.data.directory", "data/test");
    }

    @BeforeEach
    public void init() {
        htmlDataReader = new HtmlDataReader();
    }

    @Test
    public void testReadDataWithValidHtml() {
        String filePath = Paths.get(dataDir, "valid.html").toString();
        List<Map<String, String>> data = htmlDataReader.readData(filePath);

        assertNotNull(data);
        assertEquals(2, data.size());
        assertEquals("Saroar", data.get(0).get("name"));
        assertEquals("28", data.get(0).get("age"));
        assertEquals("Alice", data.get(1).get("name"));
        assertEquals("25", data.get(1).get("age"));
    }

    @Test
    public void testReadDataWithoutDirectoryTable() {
        String filePath = Paths.get(dataDir, "without-directory.html").toString();

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> htmlDataReader.readData(filePath));

        assertEquals("Table with id 'directory' not found in the HTML file",
                exception.getMessage());
    }

    @Test
    public void testReadDataWithEmptyHtml() {
        String filePath = Paths.get(dataDir, "empty.html").toString();

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> htmlDataReader.readData(filePath));

        assertEquals("Table with id 'directory' not found in the HTML file",
                exception.getMessage());
    }
}
