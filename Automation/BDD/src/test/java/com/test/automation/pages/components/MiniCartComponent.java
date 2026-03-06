package com.test.automation.pages.components;

import com.test.automation.utils.MoneyUtils;
import com.test.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.util.List;

public class MiniCartComponent {

    private final WebDriver driver;
    private final WaitUtils wait;

    private final By miniCartRoot = By.cssSelector("[data-testid='mini-cart']");
    private final By closeButton = By.cssSelector("[data-testid='mini-cart-close'], [aria-label='Close']");
    private final By viewCartLink = By.cssSelector("[data-testid='view-cart']");

    private final By errorState = By.cssSelector("[data-testid='mini-cart-error']");
    private final By errorRetry = By.cssSelector("[data-testid='mini-cart-retry']");

    private final By lineItems = By.cssSelector("[data-testid='mini-cart-item']");

    private final By subtotal = By.cssSelector("[data-testid='mini-cart-subtotal'], [data-testid='cart-subtotal']");
    private final By total = By.cssSelector("[data-testid='mini-cart-total'], [data-testid='cart-total']");

    public MiniCartComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    public void waitForOpen() {
        wait.visible(miniCartRoot);
    }

    public void close() {
        if (wait.isVisible(closeButton)) {
            wait.clickable(closeButton).click();
            wait.untilInvisible(miniCartRoot);
        }
    }

    public void clickViewCart() {
        wait.clickable(viewCartLink).click();
    }

    public boolean isErrorStateVisible() {
        return wait.isVisible(errorState);
    }

    public void clickRetryOnErrorState() {
        wait.clickable(errorRetry).click();
    }

    public WebElement getLineItemBySku(String sku) {
        waitForOpen();
        List<WebElement> items = driver.findElements(lineItems);
        for (WebElement item : items) {
            String dataSku = item.getAttribute("data-sku");
            if (sku.equalsIgnoreCase(dataSku)) {
                return item;
            }
        }
        throw new AssertionError("SKU not found in mini-cart: " + sku);
    }

    public boolean isSkuPresent(String sku) {
        try {
            getLineItemBySku(sku);
            return true;
        } catch (AssertionError ex) {
            return false;
        }
    }

    public int getQty(String sku) {
        WebElement item = getLineItemBySku(sku);
        WebElement qtyInput = item.findElement(By.cssSelector("[data-testid='qty-input'], input[name='quantity']"));
        String val = qtyInput.getAttribute("value");
        return Integer.parseInt(val.replaceAll("[^0-9]", ""));
    }

    public void setQty(String sku, int qty) {
        WebElement item = getLineItemBySku(sku);
        WebElement qtyInput = item.findElement(By.cssSelector("[data-testid='qty-input'], input[name='quantity']"));
        qtyInput.click();
        qtyInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        qtyInput.sendKeys(String.valueOf(qty));
        qtyInput.sendKeys(Keys.TAB);
    }

    public void removeSku(String sku) {
        WebElement item = getLineItemBySku(sku);
        item.findElement(By.cssSelector("[data-testid='remove-item'], [aria-label='Remove']")).click();
    }

    public String getName(String sku) {
        WebElement item = getLineItemBySku(sku);
        return item.findElement(By.cssSelector("[data-testid='item-name']")).getText();
    }

    public BigDecimal getUnitPrice(String sku) {
        WebElement item = getLineItemBySku(sku);
        String raw = item.findElement(By.cssSelector("[data-testid='unit-price']")).getText();
        return MoneyUtils.parseMoney(raw);
    }

    public BigDecimal getLineTotal(String sku) {
        WebElement item = getLineItemBySku(sku);
        String raw = item.findElement(By.cssSelector("[data-testid='line-total']")).getText();
        return MoneyUtils.parseMoney(raw);
    }

    public BigDecimal getDisplayedTotal() {
        String raw = wait.visible(total).getText();
        return MoneyUtils.parseMoney(raw);
    }

    public BigDecimal getDisplayedSubtotal() {
        if (!wait.isVisible(subtotal)) {
            return BigDecimal.ZERO;
        }
        String raw = driver.findElement(subtotal).getText();
        return MoneyUtils.parseMoney(raw);
    }

    public BigDecimal calculateSumOfLineTotals() {
        waitForOpen();
        BigDecimal sum = BigDecimal.ZERO;
        for (WebElement item : driver.findElements(lineItems)) {
            String raw = item.findElement(By.cssSelector("[data-testid='line-total']")).getText();
            sum = sum.add(MoneyUtils.parseMoney(raw));
        }
        return sum;
    }

    public int getSumOfQuantities() {
        waitForOpen();
        int sum = 0;
        for (WebElement item : driver.findElements(lineItems)) {
            WebElement qtyInput = item.findElement(By.cssSelector("[data-testid='qty-input'], input[name='quantity']"));
            String val = qtyInput.getAttribute("value");
            sum += Integer.parseInt(val.replaceAll("[^0-9]", ""));
        }
        return sum;
    }
}
