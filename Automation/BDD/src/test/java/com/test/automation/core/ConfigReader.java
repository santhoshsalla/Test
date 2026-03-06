package com.test.automation.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final String CONFIG_PATH = "config/config.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_PATH)) {
            if (is == null) {
                throw new IllegalStateException("Missing config file on classpath: " + CONFIG_PATH);
            }
            PROPERTIES.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config file: " + CONFIG_PATH, e);
        }
    }

    private ConfigReader() {
    }

    public static String get(String key) {
        String value = PROPERTIES.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Missing config property: " + key);
        }
        return value.trim();
    }

    public static String getOptional(String key, String defaultValue) {
        String value = PROPERTIES.getProperty(key);
        return value == null ? defaultValue : value.trim();
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.parseBoolean(getOptional(key, String.valueOf(defaultValue)));
    }

    public static long getLong(String key, long defaultValue) {
        try {
            return Long.parseLong(getOptional(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }
}
