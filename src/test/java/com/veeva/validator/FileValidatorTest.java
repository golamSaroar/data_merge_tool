package com.veeva.validator;

import static org.junit.jupiter.api.Assertions.*;

import com.veeva.config.ConfigurationManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Paths;

public class FileValidatorTest {

    private static String dataDir;

    @BeforeAll
    static void setUp() {
        ConfigurationManager configManager = ConfigurationManager.getInstance();
        dataDir = configManager.getProperty("app.data.directory", "data");
    }

    @Test
    void testValidateFileWithValidPath() throws IOException {
        String validPath = Paths.get(dataDir, "first.html").toString();
        String validatedPath = FileValidator.validateFile(validPath);
        assertNotNull(validatedPath);
    }

    @Test
    void testValidateFileWithNonExistentFile() {
        String nonExistentPath = Paths.get(dataDir, "non_existent_file.txt").toString();
        assertThrows(IllegalArgumentException.class, () -> FileValidator.validateFile(nonExistentPath));
    }

    @Test
    void testValidateFileWithLargeFileSize() {
        String largeFilePath = Paths.get(dataDir, "toolarge.csv").toString();
        assertThrows(IllegalArgumentException.class, () -> FileValidator.validateFile(largeFilePath));
    }

    @Test
    void testValidateFileWithInsecurePath() {
        String insecurePath = Paths.get(dataDir, "../outside_safe_directory/file.txt").toString();
        assertThrows(SecurityException.class, () -> FileValidator.validateFile(insecurePath));
    }
}
