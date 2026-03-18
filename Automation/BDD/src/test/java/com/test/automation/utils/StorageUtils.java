package com.test.automation.utils;

import com.test.automation.core.ConfigReader;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public final class StorageUtils {

    private StorageUtils() {
    }

    public static void corruptCartStorageAndReload(WebDriver driver) {
        String storageKey = ConfigReader.getOptional("cartStorageKey", "cart");
        ((JavascriptExecutor) driver).executeScript("window.localStorage.setItem(arguments[0], arguments[1]);", storageKey, "{bad-json");
        driver.navigate().refresh();
    }

    public static void revisitSiteSameProfile(WebDriver driver) {
        String baseUrl = ConfigReader.get("baseUrl");

        // Simulate closing current tab and reopening the site within the same browser profile.
        ((JavascriptExecutor) driver).executeScript("window.open('about:blank','_blank');");
        String original = driver.getWindowHandle();

        // Switch to the newest tab
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(original)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        // Close old tab
        driver.switchTo().window(original);
        driver.close();

        // Switch back to remaining tab
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
            break;
        }

        driver.get(baseUrl);
    }
}
