package com.veeva.config;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConfigurationManagerTest {

    ConfigurationManager configManager;

    @Before
    public void setUp() {
        System.setProperty("active.profile", "qa");
        configManager = ConfigurationManager.getInstance();
    }

    @Test
    public void testInitialization() {
        assertNotNull(configManager);
    }

    @Test
    public void testProfileSpecificConfig() {
        String propertyValue = configManager.getProperty("app.data.directory");
        assertEquals("data/test", propertyValue);
    }
}
