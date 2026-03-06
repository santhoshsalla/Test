package com.test.automation.pages;

import com.test.automation.core.ConfigReader;
import com.test.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class PdpPage {

    private final WebDriver driver;
    private final WaitUtils wait;

    private final By pdpRoot = By.cssSelector("[data-testid='pdp'], [data-page='pdp']");
    private final By addToCart = By.cssSelector("[data-testid='add-to-cart'], button[aria-label='Add to cart']");

    // Optional variant controls (update selectors to match AUT)
    private final By variantDropdowns = By.cssSelector("[data-testid='variant-select'] select");
    private final By variantOptions = By.cssSelector("option:not([disabled])");

    public PdpPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    public void openForSku(String sku) {
        String baseUrl = ConfigReader.get("baseUrl");
        String template = ConfigReader.getOptional("pdpPathTemplate", "/pdp/%s");
        driver.get(baseUrl + String.format(template, sku));
        waitForLoad();
    }

    public void waitForLoad() {
        if (!wait.isVisible(pdpRoot)) {
            wait.visible(addToCart);
        }
    }

    public void selectRequiredVariantsIfPresent() {
        List<org.openqa.selenium.WebElement> selects = driver.findElements(variantDropdowns);
        for (org.openqa.selenium.WebElement select : selects) {
            select.click();
            List<org.openqa.selenium.WebElement> options = select.findElements(variantOptions);
            if (!options.isEmpty()) {
                options.get(0).click();
            }
        }
    }

    public void addToCart() {
        wait.clickable(addToCart).click();
    }
}
