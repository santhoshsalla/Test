package com.example.automation.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected TestConfig config;

    protected void setUp() {
        this.config = TestConfig.load();

        String browser = config.getBrowser();
        boolean headless = config.isHeadless();

        if ("firefox".equalsIgnoreCase(browser)) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            if (headless) {
                options.addArguments("-headless");
            }
            this.driver = new FirefoxDriver(options);
        } else {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            if (headless) {
                options.addArguments("--headless=new");
            }
            options.addArguments("--window-size=1920,1080");
            this.driver = new ChromeDriver(options);
        }

        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWaitSeconds()));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(config.getExplicitWaitSeconds()));
    }

    protected void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
