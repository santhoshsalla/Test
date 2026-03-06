package com.automation.cart.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class WaitUtils {

    private WaitUtils() {
    }

    public static WebElement waitForVisible(WebDriver driver, By locator, Duration timeout) {
        return new WebDriverWait(driver, timeout)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickable(WebDriver driver, By locator, Duration timeout) {
        return new WebDriverWait(driver, timeout)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static boolean waitForInvisible(WebDriver driver, By locator, Duration timeout) {
        return new WebDriverWait(driver, timeout)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static void waitForTextToBePresent(WebDriver driver, By locator, String text, Duration timeout) {
        new WebDriverWait(driver, timeout)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public static void waitForDocumentReady(WebDriver driver, Duration timeout) {
        new WebDriverWait(driver, timeout).until(d ->
                ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
    }
}
