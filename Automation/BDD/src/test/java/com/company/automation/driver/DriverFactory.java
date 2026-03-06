package com.company.automation.driver;

import com.company.automation.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.Map;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver createDefaultDriver() {
        String browser = ConfigReader.get("browser", "chrome").toLowerCase();
        switch (browser) {
            case "firefox":
                return createFirefox();
            case "chrome":
            default:
                return createChrome(false);
        }
    }

    public static WebDriver createChromeWithMobileEmulation() {
        return createChrome(true);
    }

    private static WebDriver createChrome(boolean mobileEmulation) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        if (mobileEmulation) {
            options.setExperimentalOption("mobileEmulation", Map.of("deviceName", "Pixel 5"));
        }
        WebDriver driver = new ChromeDriver(options);
        applyTimeouts(driver);
        return driver;
    }

    private static WebDriver createFirefox() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        WebDriver driver = new FirefoxDriver(options);
        applyTimeouts(driver);
        return driver;
    }

    private static void applyTimeouts(WebDriver driver) {
        long pageLoad = Long.parseLong(ConfigReader.get("pageLoadTimeout", "30"));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoad));
        driver.manage().window().maximize();
    }
}
