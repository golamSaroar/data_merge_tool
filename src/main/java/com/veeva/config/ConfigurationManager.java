package com.veeva.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {
    private static final Logger logger = LogManager.getLogger();

    private final Properties properties = new Properties();

    private static ConfigurationManager instance;


    // Enum to represent valid profiles
    public enum Profile {
        DEV, PROD, QA
    }

    private ConfigurationManager() {
        try {
            loadDefaultConfig();
            String activeProfile = System.getProperty("active.profile");
            if (isValidProfile(activeProfile)) {
                loadProfileConfig(activeProfile);
            }
        } catch (Exception e) {
            logger.error("Error initializing ConfigurationManager: {}", e.getMessage(), e);
            throw e;
        }
    }

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }

    private void loadDefaultConfig() {
        try (InputStream inputStream = ConfigurationManager.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                logger.error("Default config file not found.");
                throw new RuntimeException("Default config file not found.");
            }
        } catch (IOException e) {
            logger.error("Error loading default config file: {}", e.getMessage(), e);
            throw new RuntimeException("Error loading default config file.", e);
        }
    }

    private void loadProfileConfig(String profile) {
        if (profile != null && !profile.isEmpty()) {
            String profileConfigFile = "config-" + profile + ".properties";

            try (InputStream inputStream = ConfigurationManager.class.getClassLoader()
                    .getResourceAsStream(profileConfigFile)) {
                if (inputStream != null) {
                    properties.load(inputStream);
                }
            } catch (IOException e) {
                logger.error("Error loading {} file: {}", profileConfigFile, e.getMessage(), e);
                throw new RuntimeException("Error loading '" + profileConfigFile + "' file.'", e);
            }
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    private boolean isValidProfile(String profile) {
        if (profile == null || profile.isEmpty()) {
            return false;
        }

        // check against enum values
        try {
            Profile.valueOf(profile.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            logger.error("Invalid config profile is passed at runtime: {}", profile, e);
            return false;
        }
    }
}
