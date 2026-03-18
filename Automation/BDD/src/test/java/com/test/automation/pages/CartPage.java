package com.test.automation.pages;

import com.test.automation.core.ConfigReader;
import com.test.automation.utils.MoneyUtils;
import com.test.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.util.List;

public class CartPage {

    private final WebDriver driver;
    private final WaitUtils wait;

    private final By cartRoot = By.cssSelector("[data-testid='cart-page'], [data-page='cart']");
    private final By cartItems = By.cssSelector("[data-testid='cart-item']");
    private final By cartTotal = By.cssSelector("[data-testid='cart-total']");
    private final By cartMessage = By.cssSelector("[data-testid='cart-message'], [role='alert']");
    private final By retryButton = By.cssSelector("[data-testid='retry'], [data-testid='cart-retry']");
    private final By emptyState = By.cssSelector("[data-testid='cart-empty'], [data-testid='empty-cart']");
    private final By proceedToCheckout = By.cssSelector("[data-testid='proceed-to-checkout'], [data-testid='checkout'], button[aria-label='Proceed to checkout']");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    public void open() {
        String baseUrl = ConfigReader.get("baseUrl");
        String cartPath = ConfigReader.getOptional("cartPath", "/cart");
        driver.get(baseUrl + cartPath);
        waitForLoad();
    }

    public void waitForLoad() {
        if (!wait.isVisible(cartRoot)) {
            wait.visible(cartItems);
        }
    }

    public WebElement getRowBySku(String sku) {
        waitForLoad();
        List<WebElement> items = driver.findElements(cartItems);
        for (WebElement item : items) {
            String dataSku = item.getAttribute("data-sku");
            if (sku.equalsIgnoreCase(dataSku)) {
                return item;
            }
        }
        throw new AssertionError("SKU not found on cart page: " + sku);
    }

    public boolean isSkuPresent(String sku) {
        try {
            getRowBySku(sku);
            return true;
        } catch (AssertionError ex) {
            return false;
        }
    }

    public int getQty(String sku) {
        WebElement row = getRowBySku(sku);
        WebElement qtyInput = row.findElement(By.cssSelector("[data-testid='qty-input'], input[name='quantity']"));
        String val = qtyInput.getAttribute("value");
        return Integer.parseInt(val.replaceAll("[^0-9]", ""));
    }

    public void setQtyAndApply(String sku, String qtyRaw) {
        WebElement row = getRowBySku(sku);
        WebElement qtyInput = row.findElement(By.cssSelector("[data-testid='qty-input'], input[name='quantity']"));
        qtyInput.click();
        qtyInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        qtyInput.sendKeys(qtyRaw);

        // If an explicit apply/update button exists, click it; otherwise blur to trigger update.
        List<WebElement> applyButtons = row.findElements(By.cssSelector("[data-testid='update-qty'], button[aria-label='Update']"));
        if (!applyButtons.isEmpty()) {
            applyButtons.get(0).click();
        } else {
            qtyInput.sendKeys(Keys.TAB);
        }
    }

    public void removeSku(String sku) {
        WebElement row = getRowBySku(sku);
        row.findElement(By.cssSelector("[data-testid='remove-item'], button[aria-label='Remove']")).click();

        // Handle optional confirm dialog
        By confirm = By.cssSelector("[data-testid='confirm-remove'], button[data-action='confirm']");
        if (wait.isVisible(confirm)) {
            driver.findElement(confirm).click();
        }
    }

    public BigDecimal getLineTotal(String sku) {
        WebElement row = getRowBySku(sku);
        String raw = row.findElement(By.cssSelector("[data-testid='line-total']")).getText();
        return MoneyUtils.parseMoney(raw);
    }

    public String getName(String sku) {
        WebElement row = getRowBySku(sku);
        return row.findElement(By.cssSelector("[data-testid='item-name']")).getText();
    }

    public BigDecimal getUnitPrice(String sku) {
        WebElement row = getRowBySku(sku);
        String raw = row.findElement(By.cssSelector("[data-testid='unit-price']")).getText();
        return MoneyUtils.parseMoney(raw);
    }

    public boolean hasQuantityControl(String sku) {
        WebElement row = getRowBySku(sku);
        return !row.findElements(By.cssSelector("[data-testid='qty-input'], input[name='quantity']")).isEmpty();
    }

    public BigDecimal getDisplayedTotal() {
        String raw = wait.visible(cartTotal).getText();
        return MoneyUtils.parseMoney(raw);
    }

    public boolean isMessageVisible() {
        return wait.isVisible(cartMessage);
    }

    public String getMessageText() {
        return wait.visible(cartMessage).getText();
    }

    public void clickRetryIfPresent() {
        if (wait.isVisible(retryButton)) {
            driver.findElement(retryButton).click();
        }
    }
}
