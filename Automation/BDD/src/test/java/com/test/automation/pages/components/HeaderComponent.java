package com.test.automation.pages.components;

import com.test.automation.utils.MoneyUtils;
import com.test.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;

public class HeaderComponent {

    private final WebDriver driver;
    private final WaitUtils wait;

    private final By cartIcon = By.cssSelector("[data-testid='header-cart-icon'], [aria-label='Cart']");
    private final By cartBadge = By.cssSelector("[data-testid='cart-badge']");

    public HeaderComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    public int getBadgeCount() {
        if (!wait.isVisible(cartBadge)) {
            return 0;
        }
        String raw = driver.findElement(cartBadge).getText();
        if (raw == null || raw.isBlank()) {
            return 0;
        }
        return Integer.parseInt(raw.replaceAll("[^0-9]", ""));
    }

    public void openMiniCart() {
        wait.clickable(cartIcon).click();
    }
}
