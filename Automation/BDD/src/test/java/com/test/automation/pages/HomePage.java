package com.test.automation.pages;

import com.test.automation.core.ConfigReader;
import com.test.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    private final WebDriver driver;
    private final WaitUtils wait;

    private final By header = By.cssSelector("header, [data-testid='header']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    public void open() {
        String baseUrl = ConfigReader.get("baseUrl");
        driver.get(baseUrl);
        waitForLoad();
    }

    public void waitForLoad() {
        // Basic readiness check; update selector to match AUT if needed.
        wait.isVisible(header);
    }
}
