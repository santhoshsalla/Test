package com.example.automation.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig {

    private final Properties props;

    private TestConfig(Properties props) {
        this.props = props;
    }

    public static TestConfig load() {
        Properties props = new Properties();
        try (InputStream is = TestConfig.class.getClassLoader().getResourceAsStream("config/config.properties")) {
            if (is != null) {
                props.load(is);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load config/config.properties", e);
        }

        // Allow overriding via -Dkey=value
        System.getProperties().forEach((k, v) -> props.put(k, v));

        return new TestConfig(props);
    }

    public String getBaseUrl() {
        return getRequired("baseUrl");
    }

    public String getPlpPath() {
        return getRequired("plpPath");
    }

    public String getPdpPathTemplate() {
        return getRequired("pdpPathTemplate");
    }

    public String getCartPath() {
        return getRequired("cartPath");
    }

    public String getBrowser() {
        return props.getProperty("browser", "chrome");
    }

    public boolean isHeadless() {
        return Boolean.parseBoolean(props.getProperty("headless", "false"));
    }

    public long getImplicitWaitSeconds() {
        return Long.parseLong(props.getProperty("implicitWaitSeconds", "0"));
    }

    public long getExplicitWaitSeconds() {
        return Long.parseLong(props.getProperty("explicitWaitSeconds", "15"));
    }

    private String getRequired(String key) {
        String val = props.getProperty(key);
        if (val == null || val.isBlank()) {
            throw new IllegalStateException("Missing required config key: " + key);
        }
        return val.trim();
    }
}
