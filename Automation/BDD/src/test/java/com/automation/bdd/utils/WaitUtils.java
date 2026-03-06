package com.automation.bdd.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class WaitUtils {

    private WaitUtils() {
    }

    public static WebDriverWait wait(WebDriver driver) {
        long seconds = Long.parseLong(ConfigReader.get("explicitWaitSeconds", "15"));
        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }

    public static WebElement visible(WebDriver driver, By locator) {
        return wait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement clickable(WebDriver driver, By locator) {
        return wait(driver).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void invisible(WebDriver driver, By locator) {
        wait(driver).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static void documentReady(WebDriver driver) {
        wait(driver).until(d -> {
            Object readyState = ((JavascriptExecutor) d).executeScript("return document.readyState");
            return "complete".equals(String.valueOf(readyState));
        });
    }
}
