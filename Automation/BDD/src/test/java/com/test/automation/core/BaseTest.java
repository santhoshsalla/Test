package com.test.automation.core;

import org.openqa.selenium.WebDriver;

public abstract class BaseTest {

    protected WebDriver driver;

    protected void setUp() {
        DriverManager.initDriver();
        this.driver = DriverManager.getDriver();
    }

    protected void tearDown() {
        DriverManager.quitDriver();
    }
}
