package com.automation.cart.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final String CONFIG_FILE = "/config/config.properties";
    private static final Properties PROPS = new Properties();

    static {
        try (InputStream is = ConfigReader.class.getResourceAsStream(CONFIG_FILE)) {
            if (is == null) {
                throw new IllegalStateException("Unable to find config file on classpath: " + CONFIG_FILE);
            }
            PROPS.load(is);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private ConfigReader() {
    }

    public static String get(String key) {
        String value = System.getProperty(key);
        if (value != null && !value.isBlank()) {
            return value.trim();
        }
        value = PROPS.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Missing config key: " + key);
        }
        return value.trim();
    }

    public static String getOptional(String key) {
        String value = System.getProperty(key);
        if (value != null && !value.isBlank()) {
            return value.trim();
        }
        value = PROPS.getProperty(key);
        return value == null ? null : value.trim();
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
