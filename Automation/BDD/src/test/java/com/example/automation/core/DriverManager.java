package com.example.automation.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> WAIT = new ThreadLocal<>();
    private static final ThreadLocal<TestConfig> CONFIG = new ThreadLocal<>();

    private DriverManager() {
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static WebDriverWait getWait() {
        return WAIT.get();
    }

    public static TestConfig getConfig() {
        return CONFIG.get();
    }

    static void set(WebDriver driver, WebDriverWait wait, TestConfig config) {
        DRIVER.set(driver);
        WAIT.set(wait);
        CONFIG.set(config);
    }

    static void clear() {
        DRIVER.remove();
        WAIT.remove();
        CONFIG.remove();
    }
}
