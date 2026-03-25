package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PaymentsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By sendMoneyMenu = By.id("menu-send-money");
    private By upiIdOption = By.id("option-upi-id");
    private By upiInput = By.id("upi-id-input");
    private By validateButton = By.id("validate-upi");
    private By amountInput = By.id("amount-input");
    private By continueButton = By.id("continue-btn");

    public PaymentsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToUPI() {
        wait.until(ExpectedConditions.elementToBeClickable(sendMoneyMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(upiIdOption)).click();
    }

    public void enterUPIAndValidate(String upiId) {
        WebElement upiEl = wait.until(ExpectedConditions.visibilityOfElementLocated(upiInput));
        upiEl.clear();
        upiEl.sendKeys(upiId);
        driver.findElement(validateButton).click();
    }

    public void enterAmountAndContinue(String amount) {
        WebElement amountEl = wait.until(ExpectedConditions.visibilityOfElementLocated(amountInput));
        amountEl.clear();
        amountEl.sendKeys(amount);
        driver.findElement(continueButton).click();
    }

    public boolean isPayeeValidated() {
        By validatedMsg = By.cssSelector(".payee-validated");
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(validatedMsg)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
