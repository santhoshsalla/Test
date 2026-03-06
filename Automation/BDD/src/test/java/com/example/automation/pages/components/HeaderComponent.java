package com.example.automation.pages.components;

import com.example.automation.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HeaderComponent extends BasePage {

    // Recommended: add data-testid attributes in AUT and update locators accordingly
    private final By cartIcon = By.cssSelector("[data-testid='cart-icon'], a[href*='cart'], button[aria-label*='Cart']");
    private final By cartBadge = By.cssSelector("[data-testid='cart-badge'], .cart-badge, [aria-label*='Cart'] .badge");

    public HeaderComponent(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public int getCartBadgeCount() {
        try {
            String txt = driver.findElement(cartBadge).getText().trim();
            if (txt.isBlank()) {
                return 0;
            }
            return Integer.parseInt(txt.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    public void openMiniCart() {
        click(cartIcon);
    }
}
