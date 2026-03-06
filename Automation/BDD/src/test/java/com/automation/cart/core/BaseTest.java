package com.automation.cart.core;

import org.openqa.selenium.WebDriver;

public abstract class BaseTest {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    protected void setDriver(WebDriver driver) {
        DRIVER.set(driver);
    }

    public WebDriver getDriver() {
        return DRIVER.get();
    }

    protected void cleanupDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}
