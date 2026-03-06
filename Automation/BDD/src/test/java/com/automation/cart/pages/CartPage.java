package com.automation.cart.pages;

import com.automation.cart.config.ConfigReader;
import com.automation.cart.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    // Update locators to match AUT
    private final By cartPageRoot = By.cssSelector("[data-testid='cart-page'], .cart-page, main");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAt() {
        WaitUtils.waitForDocumentReady(driver, timeout);
        return driver.getCurrentUrl().contains(ConfigReader.get("cart.url")) && !driver.findElements(cartPageRoot).isEmpty();
    }
}
