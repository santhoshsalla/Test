package com.automation.cart.pages.components;

import com.automation.cart.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HeaderComponent extends BasePage {

    // Update locators to match AUT
    private final By cartIcon = By.cssSelector("[data-testid='header-cart'], a[href*='cart'], button[aria-label*='Cart']");
    private final By cartCount = By.cssSelector("[data-testid='cart-count'], .cart-count, [aria-label*='Cart'] .badge");

    public HeaderComponent(WebDriver driver) {
        super(driver);
    }

    public int getCartCount() {
        String text = visible(cartCount).getText().trim();
        if (text.isBlank()) {
            return 0;
        }
        return Integer.parseInt(text.replaceAll("[^0-9]", ""));
    }

    public void openMiniCartDrawer() {
        clickable(cartIcon).click();
    }
}
