package com.company.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PaymentPage {
    private WebDriver driver;

    public PaymentPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "payNowBtn")
    private WebElement payNowButton;

    @FindBy(id = "payByCardOption")
    private WebElement payByCardOption;

    // Card fields
    @FindBy(id = "cardNumber")
    private WebElement cardNumberInput;

    @FindBy(id = "expiry")
    private WebElement expiryInput;

    @FindBy(id = "cvv")
    private WebElement cvvInput;

    @FindBy(id = "cardHolderName")
    private WebElement cardHolderNameInput;

    @FindBy(id = "confirmPaymentBtn")
    private WebElement confirmPaymentBtn;

    @FindBy(id = "paymentStatus")
    private WebElement paymentStatusLabel;

    public void clickPayNow() { payNowButton.click(); }
    public void selectPayByCard() { payByCardOption.click(); }
    public void enterCardNumber(String num) { cardNumberInput.sendKeys(num); }
    public void enterExpiry(String exp) { expiryInput.sendKeys(exp); }
    public void enterCvv(String c) { cvvInput.sendKeys(c); }
    public void enterCardHolderName(String name) { cardHolderNameInput.sendKeys(name); }
    public void confirmPayment() { confirmPaymentBtn.click(); }
    public String getPaymentStatus() { return paymentStatusLabel.getText(); }
}
