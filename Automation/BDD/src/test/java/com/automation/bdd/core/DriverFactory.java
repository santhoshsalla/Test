package com.automation.bdd.core;

import com.automation.bdd.utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import java.time.Duration;
import java.util.Locale;
import java.util.logging.Level;

public final class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void createDriver() {
        if (DRIVER.get() != null) {
            return;
        }

        String browser = ConfigReader.get("browser", "chrome").toLowerCase(Locale.ROOT);
        boolean headless = Boolean.parseBoolean(ConfigReader.get("headless", "false"));

        if (!"chrome".equals(browser)) {
            throw new IllegalArgumentException("Only 'chrome' is supported in this sample. Configure browser=chrome");
        }

        WebDriverManager.chromedriver().setup();

        LoggingPreferences loggingPrefs = new LoggingPreferences();
        loggingPrefs.enable(LogType.PERFORMANCE, Level.ALL);

        ChromeOptions options = new ChromeOptions();
        options.setCapability("goog:loggingPrefs", loggingPrefs);
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-size=1440,900");

        WebDriver driver = new ChromeDriver(options);

        long pageLoadTimeoutSeconds = Long.parseLong(ConfigReader.get("pageLoadTimeoutSeconds", "30"));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeoutSeconds));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));

        DRIVER.set(driver);
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}
