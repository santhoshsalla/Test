package com.automation.cart.pages;

import com.automation.cart.config.ConfigReader;
import com.automation.cart.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductListingPage extends BasePage {

    // Update locators to match AUT
    private final By productGrid = By.cssSelector("[data-testid='plp-grid'], .product-grid, .products");

    public ProductListingPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(ConfigReader.get("base.url") + ConfigReader.get("plp.url"));
        WaitUtils.waitForDocumentReady(driver, timeout);
        visible(productGrid);
    }

    private By addToCartButtonBySku(String sku) {
        // Prefer data attributes in AUT like data-sku / data-testid.
        return By.cssSelector("[data-sku='" + sku + "'] [data-testid='add-to-cart'], [data-sku='" + sku + "'] button.add-to-cart");
    }

    public boolean isAddToCartVisible(String sku) {
        return !driver.findElements(addToCartButtonBySku(sku)).isEmpty() && driver.findElement(addToCartButtonBySku(sku)).isDisplayed();
    }

    public void clickAddToCart(String sku) {
        clickable(addToCartButtonBySku(sku)).click();
    }

    public void openProductPdpFromSku(String sku) {
        By productLink = By.cssSelector("[data-sku='" + sku + "'] a[href*='product']");
        clickable(productLink).click();
        WaitUtils.waitForDocumentReady(driver, timeout);
    }
}
