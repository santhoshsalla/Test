package com.company.automation.base;

import com.company.automation.config.ConfigReader;
import com.company.automation.driver.DriverFactory;
import com.company.automation.driver.DriverManager;
import org.openqa.selenium.WebDriver;

public abstract class BaseTest {

    protected void startDriver() {
        WebDriver driver = DriverFactory.createDefaultDriver();
        DriverManager.setDriver(driver);
        driver.get(ConfigReader.get("baseUrl"));
    }

    protected void stopDriver() {
        WebDriver driver = DriverManager.getDriver();
        try {
            if (driver != null) {
                driver.quit();
            }
        } finally {
            DriverManager.unload();
        }
    }
}
