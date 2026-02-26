package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.PaymentPage;

public class EnableSecureCardPaymentsTest extends BaseTest {

    @Test(description = "TC_PAY_01 - Verify successful card payment (Visa)")
    public void testSuccessfulVisaPayment() {
        String baseUrl = System.getProperty("baseUrl", "https://payment-portal.example.com");
        String username = System.getProperty("testUser", "test.user@example.com");
        String password = System.getProperty("testPassword", "Password123!");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open(baseUrl);
        loginPage.login(username, password);

        PaymentPage paymentPage = new PaymentPage(driver);
        paymentPage.openOutstandingInvoices();
        paymentPage.clickFirstInvoicePayNow();
        paymentPage.selectPayByCard();

        // Test data (sandbox card)
        String cardNumber = System.getProperty("visaCard", "4242424242424242");
        String expiry = System.getProperty("cardExpiry", "12/30");
        String cvv = System.getProperty("cardCvv", "123");
        String cardholderName = System.getProperty("cardHolder", "Test User");

        paymentPage.enterCardDetails(cardNumber, expiry, cvv, cardholderName);
        paymentPage.confirmPayment();

        // Simple assertion that payment success UI appears. In CI use backend verification where possible.
        Assert.assertTrue(paymentPage.isPaymentSuccessDisplayed(), "Payment success message should be displayed");
    }
}
