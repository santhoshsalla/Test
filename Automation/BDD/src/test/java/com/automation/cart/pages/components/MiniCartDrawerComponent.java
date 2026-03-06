package com.automation.cart.pages.components;

import com.automation.cart.pages.BasePage;
import com.automation.cart.utils.MoneyUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;

public class MiniCartDrawerComponent extends BasePage {

    // Update locators to match AUT
    private final By drawerContainer = By.cssSelector("[data-testid='mini-cart'], .mini-cart, [role='dialog'][data-cart]");
    private final By closeButton = By.cssSelector("[data-testid='mini-cart-close'], .mini-cart__close, button[aria-label*='Close']");
    private final By subtotalValue = By.cssSelector("[data-testid='mini-cart-subtotal'], .mini-cart__subtotal .amount, [data-testid='subtotal']");
    private final By emptyState = By.cssSelector("[data-testid='mini-cart-empty'], .mini-cart__empty, .cart-empty");
    private final By viewCartLink = By.cssSelector("[data-testid='view-cart'], a[href*='/cart'], a[href*='cart']");

    public MiniCartDrawerComponent(WebDriver driver) {
        super(driver);
    }

    public void waitUntilOpen() {
        visible(drawerContainer);
    }

    public void close() {
        clickable(closeButton).click();
    }

    public boolean isEmptyStateVisible() {
        return !driver.findElements(emptyState).isEmpty() && driver.findElement(emptyState).isDisplayed();
    }

    private By itemRowBySku(String sku) {
        return By.cssSelector("[data-testid='mini-cart-item'][data-sku='" + sku + "'], [data-sku='" + sku + "'].mini-cart-item");
    }

    private WebElement itemRow(String sku) {
        return visible(itemRowBySku(sku));
    }

    private By itemNameLocator() {
        return By.cssSelector("[data-testid='item-name'], .item-name");
    }

    private By itemPriceLocator() {
        return By.cssSelector("[data-testid='item-price'], .item-price");
    }

    private By qtyInputLocator() {
        return By.cssSelector("input[data-testid='quantity'], input.quantity, input[name*='qty']");
    }

    private By increaseQtyLocator() {
        return By.cssSelector("button[data-testid='qty-increase'], button[aria-label*='Increase'], .qty-plus");
    }

    private By decreaseQtyLocator() {
        return By.cssSelector("button[data-testid='qty-decrease'], button[aria-label*='Decrease'], .qty-minus");
    }

    private By removeLocator() {
        return By.cssSelector("button[data-testid='remove-item'], button[aria-label*='Remove'], .remove-item");
    }

    private By lineTotalLocator() {
        return By.cssSelector("[data-testid='line-total'], .line-total .amount");
    }

    public boolean isItemPresent(String sku) {
        return !driver.findElements(itemRowBySku(sku)).isEmpty();
    }

    public void assertItemRowFieldsVisible(String sku) {
        WebElement row = itemRow(sku);
        row.findElement(itemNameLocator()).isDisplayed();
        row.findElement(itemPriceLocator()).isDisplayed();
        row.findElement(qtyInputLocator()).isDisplayed();
        row.findElement(lineTotalLocator()).isDisplayed();
    }

    public int getQuantity(String sku) {
        WebElement row = itemRow(sku);
        String value = row.findElement(qtyInputLocator()).getAttribute("value");
        return Integer.parseInt(value.trim());
    }

    public void clickIncreaseQuantity(String sku) {
        WebElement row = itemRow(sku);
        row.findElement(increaseQtyLocator()).click();
    }

    public void clickDecreaseQuantity(String sku) {
        WebElement row = itemRow(sku);
        row.findElement(decreaseQtyLocator()).click();
    }

    public boolean isDecreaseDisabled(String sku) {
        WebElement row = itemRow(sku);
        WebElement btn = row.findElement(decreaseQtyLocator());
        return !btn.isEnabled() || Boolean.parseBoolean(btn.getAttribute("aria-disabled"));
    }

    public void setQuantity(String sku, String value) {
        WebElement row = itemRow(sku);
        WebElement input = row.findElement(qtyInputLocator());
        input.click();
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(value);
        input.sendKeys(Keys.TAB);
    }

    public BigDecimal getLineTotal(String sku) {
        WebElement row = itemRow(sku);
        String text = row.findElement(lineTotalLocator()).getText();
        return MoneyUtils.parseCurrency(text);
    }

    public BigDecimal getSubtotal() {
        String text = visible(subtotalValue).getText();
        return MoneyUtils.parseCurrency(text);
    }

    public void removeItem(String sku) {
        WebElement row = itemRow(sku);
        row.findElement(removeLocator()).click();
    }

    public void clickViewCart() {
        clickable(viewCartLink).click();
    }
}
