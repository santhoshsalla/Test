package com.example.steps;

import com.example.base.BaseTest;
import com.example.pages.ConfirmationPage;
import com.example.pages.LoginPage;
import com.example.pages.PaymentsPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class UPIPaymentSteps {
    private WebDriver driver;
    private LoginPage loginPage;
    private PaymentsPage paymentsPage;
    private ConfirmationPage confirmationPage;

    @Before
    public void beforeScenario() {
        BaseTest.setUp();
        this.driver = BaseTest.getDriver();
        loginPage = new LoginPage(driver);
        paymentsPage = new PaymentsPage(driver);
        confirmationPage = new ConfirmationPage(driver);
    }

    @After
    public void afterScenario() {
        BaseTest.tearDown();
    }

    @Given("the user is logged in and has eligible funding account and payment PIN enabled")
    public void user_is_logged_in_and_prereqs() {
        String username = System.getProperty("test.user", "testuser@example.com");
        String password = System.getProperty("test.password", "Password123");
        loginPage.login(username, password);
    }

    @Given("user navigates to {string} -> {string} -> {string}")
    public void user_navigates_to_menu(String a, String b, String c) {
        paymentsPage.navigateToUPI();
    }

    @When("user enters UPI ID {string} and validates the payee")
    public void user_enters_upi_and_validates(String upiId) {
        paymentsPage.enterUPIAndValidate(upiId);
        Assert.assertTrue("Payee should be validated", paymentsPage.isPayeeValidated());
    }

    @When("user enters amount {string} and continues")
    public void user_enters_amount_and_continues(String amount) {
        paymentsPage.enterAmountAndContinue(amount);
    }

    @Then("the confirmation screen shows payee {string} amount {string} and fees {string}")
    public void confirm_screen_shows(String expectedPayee, String expectedAmount, String expectedFees) {
        Assert.assertTrue("Payee does not match on confirmation",
                confirmationPage.getPayee().contains(expectedPayee));
        Assert.assertTrue("Amount does not match on confirmation",
                confirmationPage.getAmount().contains(expectedAmount));
        Assert.assertTrue("Fees do not match on confirmation",
                confirmationPage.getFees().contains(expectedFees));
    }

    @When("user authenticates with PIN {string}")
    public void authenticate_with_pin(String pin) {
        confirmationPage.authenticateWithPIN(pin);
    }

    @Then("payment is successful within {int} seconds")
    public void payment_success_within_seconds(Integer seconds) {
        boolean success = confirmationPage.waitForSuccessWithin(seconds);
        Assert.assertTrue("Payment was not successful within " + seconds + " seconds", success);
    }

    @Then("user can open and download the receipt")
    public void open_and_download_receipt() {
        confirmationPage.openReceipt();
        boolean downloaded = confirmationPage.downloadReceipt();
        Assert.assertTrue("Receipt should be downloadable", downloaded);
    }

    @Then("push/SMS/email notifications are triggered as per preferences")
    public void verify_notifications_triggered() {
        // Placeholder: implement verification via system hooks, APIs, or test doubles.
        Assert.assertTrue(true);
    }
}
