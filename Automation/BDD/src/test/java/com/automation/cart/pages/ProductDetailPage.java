package com.automation.cart.pages;

import com.automation.cart.config.ConfigReader;
import com.automation.cart.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailPage extends BasePage {

    // Update locators to match AUT
    private final By productTitle = By.cssSelector("[data-testid='pdp-title'], h1.product-title, h1");
    private final By addToCartButton = By.cssSelector("[data-testid='add-to-cart'], button.add-to-cart, button[name='add-to-cart']");

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    public void openForSku(String sku) {
        String template = ConfigReader.get("pdp.url.template");
        driver.get(ConfigReader.get("base.url") + String.format(template, sku));
        WaitUtils.waitForDocumentReady(driver, timeout);
        visible(productTitle);
    }

    public boolean isAddToCartEnabled() {
        return visible(addToCartButton).isEnabled();
    }

    public void clickAddToCart() {
        clickable(addToCartButton).click();
    }
}
