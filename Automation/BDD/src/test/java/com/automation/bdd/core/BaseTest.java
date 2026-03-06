package com.automation.bdd.core;

import com.automation.bdd.utils.ConfigReader;
import org.openqa.selenium.WebDriver;

public abstract class BaseTest {

    protected WebDriver driver() {
        return DriverFactory.getDriver();
    }

    protected void setUp() {
        DriverFactory.createDriver();
        openBaseUrl();
    }

    protected void tearDown() {
        DriverFactory.quitDriver();
    }

    protected void openBaseUrl() {
        String baseUrl = ConfigReader.getRequired("baseUrl");
        driver().get(baseUrl);
    }
}
