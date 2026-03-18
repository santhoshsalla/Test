package com.test.automation.pages;

import com.test.automation.core.ConfigReader;
import com.test.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private final WebDriver driver;
    private final WaitUtils wait;

    // Update selectors to match AUT
    private final By usernameInput = By.cssSelector("[data-testid='login-username'], input[name='username'], input[type='email']");
    private final By passwordInput = By.cssSelector("[data-testid='login-password'], input[name='password'], input[type='password']");
    private final By submitButton = By.cssSelector("[data-testid='login-submit'], button[type='submit']");

    private final By logoutButton = By.cssSelector("[data-testid='logout'], a[href*='logout'], button[aria-label='Logout']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    public void open() {
        String baseUrl = ConfigReader.get("baseUrl");
        String loginPath = ConfigReader.getOptional("loginPath", "/login");
        driver.get(baseUrl + loginPath);
        wait.visible(usernameInput);
    }

    public void login(String username, String password) {
        wait.visible(usernameInput).clear();
        driver.findElement(usernameInput).sendKeys(username);
        wait.visible(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
        wait.clickable(submitButton).click();
    }

    public void loginWithConfiguredUser() {
        String username = ConfigReader.get("user.username");
        String password = ConfigReader.get("user.password");
        login(username, password);

        // Basic post-login stabilization; customize to your AUT.
        String postLoginPath = ConfigReader.getOptional("postLoginPath", "/");
        waitForUrlContains(postLoginPath);
    }

    public void logout() {
        if (wait.isVisible(logoutButton)) {
            wait.clickable(logoutButton).click();
            String loginPath = ConfigReader.getOptional("loginPath", "/login");
            waitForUrlContains(loginPath);
        }
    }

    private void waitForUrlContains(String path) {
        long end = System.currentTimeMillis() + 15000;
        while (System.currentTimeMillis() < end) {
            if (driver.getCurrentUrl().contains(path)) {
                return;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
