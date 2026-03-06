package com.example.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class PLPPage extends BasePage {

    private final By productCards = By.cssSelector("[data-testid='product-card'], .product-card");
    private final By toast = By.cssSelector("[data-testid='toast'], .toast, .snackbar");

    public PLPPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void waitForLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productCards));
    }

    public void addToCartFromListing(String sku) {
        WebElement card = findProductCardBySku(sku);
        if (card == null) {
            throw new IllegalStateException("SKU not found on PLP: " + sku);
        }
        card.findElement(By.cssSelector("[data-testid='add-to-cart'], button.add-to-cart, button[aria-label*='Add to cart']")).click();
    }

    public void waitForSuccessToast() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(toast));
    }

    private WebElement findProductCardBySku(String sku) {
        List<WebElement> cards = driver.findElements(productCards);
        for (WebElement card : cards) {
            String txt = card.getText();
            if (txt != null && txt.toLowerCase().contains(sku.toLowerCase())) {
                return card;
            }
        }
        return null;
    }
}
