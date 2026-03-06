package com.automation.cart.pages;

import com.automation.cart.config.ConfigReader;
import com.automation.cart.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final Duration timeout;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.timeout = Duration.ofSeconds(ConfigReader.getInt("explicit.wait.seconds"));
    }

    protected WebElement visible(By locator) {
        return WaitUtils.waitForVisible(driver, locator, timeout);
    }

    protected WebElement clickable(By locator) {
        return WaitUtils.waitForClickable(driver, locator, timeout);
    }
}
