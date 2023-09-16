package com.veeva.validator;

import static org.junit.jupiter.api.Assertions.*;

import com.veeva.config.ConfigurationManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Paths;

public class FileValidatorTest {

    private static String dataDir;

    @BeforeAll static void setUp() {
        System.setProperty("active.profile", "qa");
        ConfigurationManager configManager = ConfigurationManager.getInstance();
        dataDir = configManager.getProperty("app.data.directory", "data/test");
    }

    @Test void testValidateFileWithValidPath() throws IOException {
        String validPath = Paths.get(dataDir, "valid.html").toString();
        String validatedPath = FileValidator.validateFile(validPath);
        assertNotNull(validatedPath);
    }

    @Test void testValidateFileWithNonExistentFile() {
        String nonExistentPath = Paths.get(dataDir, "non_existent_file.txt").toString();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> FileValidator.validateFile(nonExistentPath));

        assertEquals("File does not exist.", exception.getMessage());
    }

    @Test void testValidateFileWithLargeFileSize() {
        String largeFilePath = Paths.get(dataDir, "too_large.csv").toString();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> FileValidator.validateFile(largeFilePath));

        assertEquals("File size exceeds the limit.", exception.getMessage());
    }

    @Test void testValidateFileWithInsecurePath() {
        String insecurePath = Paths.get(dataDir, "../outside_safe_directory/file.txt").toString();
        assertThrows(SecurityException.class, () -> FileValidator.validateFile(insecurePath));
    }
}
