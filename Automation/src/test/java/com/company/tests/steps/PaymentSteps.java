package com.company.tests.steps;

import com.company.pages.PaymentPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class PaymentSteps {
    private WebDriver driver;
    private PaymentPage paymentPage;

    @Before
    public void setUp() {
        // Assumes chromedriver is on PATH or WebDriverManager is configured externally
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Given("the user is logged into the payment portal")
    public void user_logged_in() {
        driver.get("https://payment-portal.example.com/login");
        // TODO: perform login steps or use a pre-authenticated session
        // For now assume user is already authenticated via a test setup
        paymentPage = new PaymentPage(driver);
    }

    @When("the user selects an unpaid invoice and chooses Pay by Card")
    public void select_invoice_and_pay_by_card() {
        paymentPage.clickPayNow();
        paymentPage.selectPayByCard();
    }

    @When("enters valid card details")
    public void enters_valid_card_details() {
        paymentPage.enterCardNumber("4111111111111111");
        paymentPage.enterExpiry("12/30");
        paymentPage.enterCvv("123");
        paymentPage.enterCardHolderName("Test User");
        paymentPage.confirmPayment();
    }

    @Then("the payment should be processed successfully")
    public void payment_processed_successfully() throws InterruptedException {
        // Wait briefly for processing (replace with explicit wait)
        Thread.sleep(3000);
        String status = paymentPage.getPaymentStatus();
        Assert.assertTrue("Payment not successful", status.contains("Success") || status.contains("Paid"));
    }

    @When("the user chooses Pay by Card and enters an expired card")
    public void expired_card_flow() {
        paymentPage.clickPayNow();
        paymentPage.selectPayByCard();
        paymentPage.enterCardNumber("4111111111111111");
        paymentPage.enterExpiry("01/20");
        paymentPage.enterCvv("123");
        paymentPage.enterCardHolderName("Test User");
        paymentPage.confirmPayment();
    }

    @Then("a clear error message about expired card should be displayed")
    public void expired_card_error() throws InterruptedException {
        Thread.sleep(2000);
        String status = paymentPage.getPaymentStatus();
        Assert.assertTrue(status.toLowerCase().contains("expired") || status.toLowerCase().contains("invalid expiry"));
    }

    @When("the user chooses Pay by Card and enters incorrect CVV")
    public void incorrect_cvv_flow() {
        paymentPage.clickPayNow();
        paymentPage.selectPayByCard();
        paymentPage.enterCardNumber("4111111111111111");
        paymentPage.enterExpiry("12/30");
        paymentPage.enterCvv("000");
        paymentPage.enterCardHolderName("Test User");
        paymentPage.confirmPayment();
    }

    @Then("a clear error message about incorrect CVV should be displayed")
    public void incorrect_cvv_error() throws InterruptedException {
        Thread.sleep(2000);
        String status = paymentPage.getPaymentStatus();
        Assert.assertTrue(status.toLowerCase().contains("cvv") || status.toLowerCase().contains("incorrect"));
    }
}
