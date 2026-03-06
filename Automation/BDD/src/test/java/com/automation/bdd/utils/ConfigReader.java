package com.automation.bdd.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is == null) {
                throw new IllegalStateException("config.properties not found under src/test/resources");
            }
            PROPS.load(is);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load config.properties", e);
        }
    }

    private ConfigReader() {
    }

    public static String getRequired(String key) {
        String value = PROPS.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("Missing required config: " + key);
        }
        return value.trim();
    }

    public static String get(String key, String defaultValue) {
        String value = PROPS.getProperty(key);
        return (value == null || value.trim().isEmpty()) ? defaultValue : value.trim();
    }
}
