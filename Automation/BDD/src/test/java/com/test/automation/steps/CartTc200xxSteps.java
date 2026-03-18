package com.test.automation.steps;

import com.test.automation.core.ConfigReader;
import com.test.automation.core.DriverManager;
import com.test.automation.core.TestContext;
import com.test.automation.pages.CartPage;
import com.test.automation.pages.HomePage;
import com.test.automation.pages.LoginPage;
import com.test.automation.pages.PdpPage;
import com.test.automation.pages.PlpPage;
import com.test.automation.pages.components.HeaderComponent;
import com.test.automation.pages.components.MiniCartComponent;
import com.test.automation.pages.components.ToastComponent;
import com.test.automation.utils.MoneyUtils;
import com.test.automation.utils.StorageUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;

public class CartTc200xxSteps {

    private WebDriver driver() {
        return DriverManager.getDriver();
    }

    private HeaderComponent header() {
        return new HeaderComponent(driver());
    }

    private ToastComponent toast() {
        return new ToastComponent(driver());
    }

    private MiniCartComponent miniCart() {
        return new MiniCartComponent(driver());
    }

    private PlpPage plp() {
        return new PlpPage(driver());
    }

    private PdpPage pdp() {
        return new PdpPage(driver());
    }

    private CartPage cartPage() {
        return new CartPage(driver());
    }

    private HomePage home() {
        return new HomePage(driver());
    }

    private LoginPage login() {
        return new LoginPage(driver());
    }

    private String skuFor(String productName) {
        return switch (productName.trim().toLowerCase()) {
            case "product a" -> ConfigReader.get("productA.sku");
            case "product b" -> ConfigReader.get("productB.sku");
            default -> throw new IllegalArgumentException("Unknown product: " + productName + " (expected 'Product A' or 'Product B')");
        };
    }

    private BigDecimal expectedPriceFor(String productName) {
        String key = switch (productName.trim().toLowerCase()) {
            case "product a" -> "productA.price";
            case "product b" -> "productB.price";
            default -> null;
        };
        if (key == null) {
            return null;
        }
        String raw = ConfigReader.getOptional(key, "");
        return raw.isBlank() ? null : MoneyUtils.parseMoney(raw);
    }

    @Given("I open the application")
    public void iOpenTheApplication() {
        home().open();
    }

    @Given("I open the PLP")
    public void iOpenThePlp() {
        plp().open();
    }

    @Given("I open the PDP for {string}")
    public void iOpenThePdpFor(String productName) {
        pdp().openForSku(skuFor(productName));
    }

    @And("I select required variant options if present")
    public void iSelectRequiredVariantOptionsIfPresent() {
        pdp().selectRequiredVariantsIfPresent();
    }

    @And("I ensure the cart is empty")
    public void iEnsureTheCartIsEmpty() {
        cartPage().open();
        cartPage().clearCart();
        Assertions.assertThat(header().getBadgeCount())
                .as("Cart badge count")
                .isEqualTo(0);
    }

    @And("I note the current cart badge count")
    public void iNoteTheCurrentCartBadgeCount() {
        TestContext.get().baselineBadgeCount = header().getBadgeCount();
    }

    @When("I add {string} to cart from PLP")
    public void iAddToCartFromPlp(String productName) {
        String sku = skuFor(productName);
        TestContext.get().lastAddedSku = sku;
        plp().addSkuToCart(sku);
    }

    @When("I add {string} to cart from PDP")
    public void iAddToCartFromPdp(String productName) {
        TestContext.get().lastAddedSku = skuFor(productName);
        pdp().addToCart();
    }

    @Then("I should see add-to-cart success message")
    public void iShouldSeeAddToCartSuccessMessage() {
        Assertions.assertThat(toast().isSuccessVisible())
                .as("Success toast")
                .isTrue();
    }

    @Then("the cart badge count should increase by {int}")
    public void theCartBadgeCountShouldIncreaseBy(int delta) {
        int expected = TestContext.get().baselineBadgeCount + delta;
        Assertions.assertThat(header().getBadgeCount())
                .as("Cart badge count")
                .isEqualTo(expected);
    }

    @Then("the cart badge count should be {int}")
    public void theCartBadgeCountShouldBe(int expected) {
        Assertions.assertThat(header().getBadgeCount())
                .as("Cart badge count")
                .isEqualTo(expected);
    }

    @When("I open the mini-cart")
    public void iOpenTheMiniCart() {
        header().openMiniCart();
        miniCart().waitForOpen();
    }

    @When("I close the mini-cart")
    public void iCloseTheMiniCart() {
        miniCart().close();
    }

    @When("I close the mini-cart using ESC")
    public void iCloseTheMiniCartUsingEsc() {
        miniCart().closeWithEsc();
    }

    @Then("mini-cart should show {string} with quantity {int}")
    public void miniCartShouldShowWithQuantity(String productName, int qty) {
        String sku = skuFor(productName);
        Assertions.assertThat(miniCart().isSkuPresent(sku)).isTrue();
        Assertions.assertThat(miniCart().getQty(sku)).isEqualTo(qty);
    }

    @Then("mini-cart should show {string} as a single line item")
    public void miniCartShouldShowAsSingleLineItem(String productName) {
        String sku = skuFor(productName);
        Assertions.assertThat(miniCart().countSkuOccurrences(sku))
                .as("Occurrences for SKU in mini-cart")
                .isEqualTo(1);
    }

    @Then("mini-cart should show correct line total and subtotal for {string}")
    public void miniCartShouldShowCorrectLineTotalAndSubtotalFor(String productName) {
        String sku = skuFor(productName);
        int qty = miniCart().getQty(sku);
        BigDecimal unit = miniCart().getUnitPrice(sku);
        BigDecimal expectedLine = MoneyUtils.multiply(unit, qty);
        Assertions.assertThat(miniCart().getLineTotal(sku))
                .as("Line total")
                .isEqualByComparingTo(expectedLine);

        BigDecimal expectedSubtotal = miniCart().calculateSumOfLineTotals();
        Assertions.assertThat(miniCart().getDisplayedSubtotal())
                .as("Mini-cart subtotal")
                .isEqualByComparingTo(expectedSubtotal);

        BigDecimal expectedPrice = expectedPriceFor(productName);
        if (expectedPrice != null) {
            Assertions.assertThat(unit)
                    .as("Unit price for %s", productName)
                    .isEqualByComparingTo(expectedPrice);
        }
    }

    @Then("mini-cart subtotal should equal sum of line totals")
    public void miniCartSubtotalShouldEqualSumOfLineTotals() {
        BigDecimal expectedSubtotal = miniCart().calculateSumOfLineTotals();
        Assertions.assertThat(miniCart().getDisplayedSubtotal())
                .as("Mini-cart subtotal")
                .isEqualByComparingTo(expectedSubtotal);
    }

    @When("I click View cart in mini-cart")
    public void iClickViewCartInMiniCart() {
        miniCart().clickViewCart();
        cartPage().waitForLoad();
    }

    @When("I navigate to full cart page")
    public void iNavigateToFullCartPage() {
        cartPage().open();
    }

    @Then("cart page should show {string} with quantity {int}")
    public void cartPageShouldShowWithQuantity(String productName, int qty) {
        String sku = skuFor(productName);
        Assertions.assertThat(cartPage().isSkuPresent(sku)).isTrue();
        Assertions.assertThat(cartPage().getQty(sku)).isEqualTo(qty);
    }

    @Then("cart page subtotal should equal sum of line totals")
    public void cartPageSubtotalShouldEqualSumOfLineTotals() {
        Assertions.assertThat(cartPage().getDisplayedTotal())
                .as("Cart page total")
                .isEqualByComparingTo(cartPage().calculateSumOfLineTotals());
    }

    @When("I increase mini-cart quantity for {string} to {int} using plus")
    public void iIncreaseMiniCartQuantityUsingPlus(String productName, int targetQty) {
        String sku = skuFor(productName);
        int current = miniCart().getQty(sku);
        while (current < targetQty) {
            miniCart().clickPlus(sku);
            current = miniCart().getQty(sku);
        }
    }

    @When("I decrease mini-cart quantity for {string} using minus")
    public void iDecreaseMiniCartQuantityUsingMinus(String productName) {
        miniCart().clickMinus(skuFor(productName));
    }

    @Then("mini-cart quantity for {string} should remain {int}")
    public void miniCartQuantityShouldRemain(String productName, int expectedQty) {
        Assertions.assertThat(miniCart().getQty(skuFor(productName))).isEqualTo(expectedQty);
    }

    @Then("mini-cart minus button for {string} should be disabled")
    public void miniCartMinusButtonForShouldBeDisabled(String productName) {
        Assertions.assertThat(miniCart().isMinusDisabled(skuFor(productName))).isTrue();
    }

    @When("I enter mini-cart quantity {string} for {string} and commit")
    public void iEnterMiniCartQuantityAndCommit(String qtyRaw, String productName) {
        miniCart().setQtyRawAndCommit(skuFor(productName), qtyRaw);
    }

    @Then("mini-cart should show an inline quantity error")
    public void miniCartShouldShowInlineQuantityError() {
        String sku = TestContext.get().lastAddedSku;
        String msg = miniCart().getInlineQtyErrorMessage(sku);
        Assertions.assertThat(msg)
                .as("Inline quantity error")
                .isNotBlank();
    }

    @Then("mini-cart should show an insufficient stock message")
    public void miniCartShouldShowAnInsufficientStockMessage() {
        String sku = TestContext.get().lastAddedSku;
        String msg = miniCart().getInlineQtyErrorMessage(sku);
        Assertions.assertThat(msg)
                .as("Stock message")
                .containsIgnoringCase("stock");
    }

    @When("I remove {string} from mini-cart")
    public void iRemoveFromMiniCart(String productName) {
        String sku = skuFor(productName);
        TestContext.get().lastRemovedSku = sku;
        miniCart().removeSku(sku);
    }

    @Then("mini-cart should not contain {string}")
    public void miniCartShouldNotContain(String productName) {
        Assertions.assertThat(miniCart().isSkuPresent(skuFor(productName))).isFalse();
    }

    @When("I remove {string} from cart page")
    public void iRemoveFromCartPage(String productName) {
        String sku = skuFor(productName);
        TestContext.get().lastRemovedSku = sku;
        cartPage().removeSku(sku);
    }

    @Then("cart page should not contain {string}")
    public void cartPageShouldNotContain(String productName) {
        Assertions.assertThat(cartPage().isSkuPresent(skuFor(productName))).isFalse();
    }

    @Then("checkout action should be disabled")
    public void checkoutActionShouldBeDisabled() {
        Assertions.assertThat(miniCart().isCheckoutEnabled())
                .as("Checkout enabled")
                .isFalse();
    }

    @Then("checkout action should be enabled")
    public void checkoutActionShouldBeEnabled() {
        Assertions.assertThat(miniCart().isCheckoutEnabled())
                .as("Checkout enabled")
                .isTrue();
    }

    @When("I click Proceed to checkout")
    public void iClickProceedToCheckout() {
        miniCart().clickProceedToCheckout();
    }

    @Then("I should be on checkout page")
    public void iShouldBeOnCheckoutPage() {
        String checkoutPath = ConfigReader.getOptional("checkoutPath", "/checkout");
        Assertions.assertThat(driver().getCurrentUrl())
                .contains(checkoutPath);
    }

    @When("I refresh the page")
    public void iRefreshThePage() {
        driver().navigate().refresh();
    }

    @When("I revisit the site in the same browser")
    public void iRevisitTheSiteInTheSameBrowser() {
        StorageUtils.revisitSiteSameProfile(driver());
    }

    @When("I corrupt the guest cart storage and reload")
    public void iCorruptTheGuestCartStorageAndReload() {
        StorageUtils.corruptCartStorageAndReload(driver());
    }

    @Given("I log in as a valid user")
    public void iLogInAsAValidUser() {
        login().open();
        login().loginWithConfiguredUser();
    }

    @When("I log out")
    public void iLogOut() {
        login().logout();
    }

    @Then("the page should remain on the same view")
    public void thePageShouldRemainOnTheSameView() {
        // Basic guard: the URL should not unexpectedly change due to mini-cart open/close.
        String expected = TestContext.get().lastViewedUrl;
        if (expected != null) {
            Assertions.assertThat(driver().getCurrentUrl()).isEqualTo(expected);
        }
    }
}
