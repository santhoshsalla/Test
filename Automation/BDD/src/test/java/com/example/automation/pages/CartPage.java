package com.example.automation.pages;

import com.example.automation.utils.MoneyUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.util.List;

public class CartPage extends BasePage {

    private final By cartRoot = By.cssSelector("[data-testid='cart-page'], .cart-page, main");
    private final By cartRows = By.cssSelector("[data-testid='cart-item'], .cart-item, table.cart-items tbody tr");
    private final By subtotalValue = By.cssSelector("[data-testid='cart-subtotal'], .cart-summary .subtotal .amount, .subtotal .amount");
    private final By emptyState = By.cssSelector("[data-testid='empty-cart'], .empty-cart, .cart-empty");

    public CartPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void waitForLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartRoot));
    }

    public boolean isItemPresent(String sku) {
        return findRowBySku(sku) != null;
    }

    public int getQuantityForSku(String sku) {
        WebElement row = findRowBySku(sku);
        if (row == null) {
            return 0;
        }
        WebElement qtyEl = row.findElement(By.cssSelector("[data-testid='qty-input'], input[name*='qty'], input.qty"));
        String val = qtyEl.getAttribute("value");
        return Integer.parseInt(val.replaceAll("[^0-9-]", ""));
    }

    public void setQuantityForSku(String sku, String quantityRaw) {
        WebElement row = findRowBySku(sku);
        if (row == null) {
            throw new IllegalStateException("SKU not found in cart: " + sku);
        }
        WebElement qtyEl = row.findElement(By.cssSelector("[data-testid='qty-input'], input[name*='qty'], input.qty"));
        qtyEl.click();
        qtyEl.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        qtyEl.sendKeys(quantityRaw);
        qtyEl.sendKeys(Keys.TAB);
    }

    public String getInlineErrorForSku(String sku) {
        WebElement row = findRowBySku(sku);
        if (row == null) {
            return "";
        }
        List<WebElement> errors = row.findElements(By.cssSelector("[data-testid='qty-error'], .error, .field-error"));
        return errors.isEmpty() ? "" : errors.get(0).getText().trim();
    }

    public void removeSku(String sku) {
        WebElement row = findRowBySku(sku);
        if (row == null) {
            return;
        }
        row.findElement(By.cssSelector("[data-testid='remove'], .remove, button[aria-label*='Remove']")).click();
        wait.until(driver -> findRowBySku(sku) == null);
    }

    public BigDecimal getSubtotal() {
        String txt = getText(subtotalValue);
        return MoneyUtils.parse(txt);
    }

    public boolean isEmptyStateVisible() {
        return !driver.findElements(emptyState).isEmpty() && driver.findElement(emptyState).isDisplayed();
    }

    private WebElement findRowBySku(String sku) {
        List<WebElement> rows = driver.findElements(cartRows);
        for (WebElement row : rows) {
            String txt = row.getText();
            if (txt != null && txt.toLowerCase().contains(sku.toLowerCase())) {
                return row;
            }
            List<WebElement> skuEls = row.findElements(By.cssSelector("[data-testid='sku'], .sku"));
            if (!skuEls.isEmpty() && skuEls.get(0).getText().trim().equalsIgnoreCase(sku)) {
                return row;
            }
        }
        return null;
    }
}
