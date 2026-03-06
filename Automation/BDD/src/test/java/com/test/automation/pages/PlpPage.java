package com.test.automation.pages;

import com.test.automation.core.ConfigReader;
import com.test.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class PlpPage {

    private final WebDriver driver;
    private final WaitUtils wait;

    private final By plpRoot = By.cssSelector("[data-testid='plp'], [data-page='plp']");
    private final By productCard = By.cssSelector("[data-testid='product-card']");

    public PlpPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    public void open() {
        String baseUrl = ConfigReader.get("baseUrl");
        String plpPath = ConfigReader.getOptional("plpPath", "/plp");
        driver.get(baseUrl + plpPath);
        waitForLoad();
    }

    public void waitForLoad() {
        // Accept either dedicated PLP root or at least one product card.
        if (!wait.isVisible(plpRoot)) {
            wait.visible(productCard);
        }
    }

    public void addSkuToCart(String sku) {
        WebElement card = getProductCardBySku(sku);
        card.findElement(By.cssSelector("[data-testid='add-to-cart'], button[aria-label='Add to cart']")).click();
    }

    public void doubleClickAddToCart(String sku) {
        WebElement card = getProductCardBySku(sku);
        WebElement addBtn = card.findElement(By.cssSelector("[data-testid='add-to-cart'], button[aria-label='Add to cart']"));
        new Actions(driver).doubleClick(addBtn).perform();
    }

    private WebElement getProductCardBySku(String sku) {
        wait.visible(productCard);
        for (WebElement card : driver.findElements(productCard)) {
            String dataSku = card.getAttribute("data-sku");
            if (sku.equalsIgnoreCase(dataSku)) {
                return card;
            }
        }
        throw new AssertionError("SKU not found on PLP: " + sku);
    }
}
