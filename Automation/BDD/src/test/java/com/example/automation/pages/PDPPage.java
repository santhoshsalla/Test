package com.example.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PDPPage extends BasePage {

    private final By addToCartButton = By.cssSelector("[data-testid='add-to-cart'], button.add-to-cart, button[aria-label*='Add to cart']");
    private final By toast = By.cssSelector("[data-testid='toast'], .toast, .snackbar");

    public PDPPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void waitForLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(addToCartButton));
    }

    public void addToCart() {
        click(addToCartButton);
    }

    public void waitForSuccessToast() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(toast));
    }
}
