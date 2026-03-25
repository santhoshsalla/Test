package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ConfirmationPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By payeeText = By.id("conf-payee");
    private By amountText = By.id("conf-amount");
    private By feesText = By.id("conf-fees");
    private By pinInput = By.id("pin-input");
    private By authenticateBtn = By.id("auth-pin-btn");
    private By successMessage = By.cssSelector(".payment-success");
    private By receiptLink = By.id("receipt-link");
    private By downloadReceipt = By.id("download-receipt");

    public ConfirmationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getPayee() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(payeeText)).getText();
    }

    public String getAmount() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(amountText)).getText();
    }

    public String getFees() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(feesText)).getText();
    }

    public void authenticateWithPIN(String pin) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(pinInput)).sendKeys(pin);
        driver.findElement(authenticateBtn).click();
    }

    public boolean waitForSuccessWithin(int seconds) {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
            shortWait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void openReceipt() {
        wait.until(ExpectedConditions.elementToBeClickable(receiptLink)).click();
    }

    public boolean downloadReceipt() {
        try {
            driver.findElement(downloadReceipt).click();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
