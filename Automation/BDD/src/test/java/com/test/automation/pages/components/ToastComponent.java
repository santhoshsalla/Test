package com.test.automation.pages.components;

import com.test.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ToastComponent {

    private final WebDriver driver;
    private final WaitUtils wait;

    private final By successToast = By.cssSelector("[data-testid='toast-success'], [data-testid='toast'][data-type='success']");
    private final By errorToast = By.cssSelector("[data-testid='toast-error'], [data-testid='toast'][data-type='error']");
    private final By toastRetry = By.cssSelector("[data-testid='toast-retry'], [data-testid='retry']");

    public ToastComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    public boolean isSuccessVisible() {
        return wait.isVisible(successToast);
    }

    public boolean isErrorVisible() {
        return wait.isVisible(errorToast);
    }

    public void clickRetryIfPresent() {
        if (wait.isVisible(toastRetry)) {
            driver.findElement(toastRetry).click();
        }
    }
}
