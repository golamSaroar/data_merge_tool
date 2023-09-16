package com.veeva.validator;

import com.veeva.config.ConfigurationManager;

import java.io.File;
import java.io.IOException;

public class FileValidator {
    private static final ConfigurationManager configManager = ConfigurationManager.getInstance();

    public static String validateFile(String userInputPath) throws IOException {
        String validatedPath = validateFilePath(userInputPath);
        validateFileSize(validatedPath);
        return validatedPath;
    }

    private static String validateFilePath(String userInputPath) throws IOException {
        File file = new File(userInputPath);
        String canonicalPath = file.getCanonicalPath();

        String safeBaseDirectory = System.getProperty("user.dir") + File.separator
                + configManager.getProperty("app.data.directory", "data");
        if (!isPathInSafeDirectory(canonicalPath, safeBaseDirectory)) {
            throw new SecurityException("File path is restricted.");
        }

        if (!file.exists()) {
            throw new IllegalArgumentException("File does not exist.");
        }

        return canonicalPath;
    }

    private static boolean isPathInSafeDirectory(String path, String safeDirectory) {
        return path.startsWith(safeDirectory);
    }

    private static void validateFileSize(String filePath) {
        File file = new File(filePath);
        long maxFileSize = Long.parseLong(configManager.getProperty("app.max.file.size.bytes", "5242880"));

        if (file.length() > maxFileSize) {
            throw new IllegalArgumentException("File size exceeds the limit.");
        }
    }
}
