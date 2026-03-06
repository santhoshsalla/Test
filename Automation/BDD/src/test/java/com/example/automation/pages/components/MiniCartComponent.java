package com.example.automation.pages.components;

import com.example.automation.pages.BasePage;
import com.example.automation.utils.MoneyUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.util.List;

public class MiniCartComponent extends BasePage {

    private final By miniCartRoot = By.cssSelector("[data-testid='mini-cart'], .mini-cart, .cart-drawer");
    private final By cartItemRows = By.cssSelector("[data-testid='mini-cart-item'], .mini-cart .cart-item, .cart-drawer .cart-item");
    private final By subtotalValue = By.cssSelector("[data-testid='mini-cart-subtotal'], .mini-cart .subtotal .amount, .cart-drawer .subtotal .amount");
    private final By goToCartButton = By.cssSelector("[data-testid='go-to-cart'], a[href*='/cart'], .mini-cart a.view-cart");

    public MiniCartComponent(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void waitUntilOpen() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(miniCartRoot));
    }

    public boolean isItemPresent(String sku) {
        return findItemRowBySku(sku) != null;
    }

    public int getQuantityForSku(String sku) {
        WebElement row = findItemRowBySku(sku);
        if (row == null) {
            return 0;
        }
        String qtyText = row.findElement(By.cssSelector("[data-testid='qty'], .qty, input[name*='qty']")).getAttribute("value");
        if (qtyText == null || qtyText.isBlank()) {
            qtyText = row.findElement(By.cssSelector("[data-testid='qty'], .qty")).getText();
        }
        return Integer.parseInt(qtyText.replaceAll("[^0-9]", ""));
    }

    public BigDecimal getSubtotal() {
        String txt = getText(subtotalValue);
        return MoneyUtils.parse(txt);
    }

    public void removeSku(String sku) {
        WebElement row = findItemRowBySku(sku);
        if (row == null) {
            return;
        }
        row.findElement(By.cssSelector("[data-testid='remove'], .remove, button[aria-label*='Remove']")).click();
        wait.until(driver -> findItemRowBySku(sku) == null);
    }

    public void goToFullCart() {
        click(goToCartButton);
    }

    private WebElement findItemRowBySku(String sku) {
        List<WebElement> rows = driver.findElements(cartItemRows);
        for (WebElement row : rows) {
            String rowText = row.getText();
            if (rowText != null && rowText.toLowerCase().contains(sku.toLowerCase())) {
                return row;
            }
            // Optional: explicit SKU element
            List<WebElement> skuEls = row.findElements(By.cssSelector("[data-testid='sku'], .sku"));
            if (!skuEls.isEmpty() && skuEls.get(0).getText().trim().equalsIgnoreCase(sku)) {
                return row;
            }
        }
        return null;
    }
}
