package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PaymentPage {
    private WebDriver driver;

    private By outstandingInvoicesNav = By.id("nav-outstanding-invoices");
    private By firstInvoicePayNow = By.cssSelector(".invoice-row:first-child .pay-now");
    private By payByCardOption = By.id("pay-by-card");
    private By cardNumberField = By.name("cardnumber");
    private By expiryField = By.name("exp-date");
    private By cvvField = By.name("cvc");
    private By cardholderNameField = By.name("cardholder-name");
    private By confirmPaymentButton = By.id("confirm-payment");
    private By successMessage = By.cssSelector(".payment-success");

    public PaymentPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openOutstandingInvoices() {
        driver.findElement(outstandingInvoicesNav).click();
    }

    public void clickFirstInvoicePayNow() {
        driver.findElement(firstInvoicePayNow).click();
    }

    public void selectPayByCard() {
        driver.findElement(payByCardOption).click();
    }

    public void enterCardDetails(String number, String expiry, String cvv, String name) {
        WebElement iframe = null; // placeholder if gateway uses iframe
        // If card fields are inside an iframe, switch to it here.
        driver.findElement(cardNumberField).sendKeys(number);
        driver.findElement(expiryField).sendKeys(expiry);
        driver.findElement(cvvField).sendKeys(cvv);
        driver.findElement(cardholderNameField).sendKeys(name);
    }

    public void confirmPayment() {
        driver.findElement(confirmPaymentButton).click();
    }

    public boolean isPaymentSuccessDisplayed() {
        return driver.findElements(successMessage).size() > 0;
    }
}
