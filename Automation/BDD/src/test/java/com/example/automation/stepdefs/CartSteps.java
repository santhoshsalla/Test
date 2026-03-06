package com.example.automation.stepdefs;

import com.example.automation.context.ScenarioCartState;
import com.example.automation.core.DriverManager;
import com.example.automation.pages.CartPage;
import com.example.automation.pages.PDPPage;
import com.example.automation.pages.PLPPage;
import com.example.automation.pages.components.HeaderComponent;
import com.example.automation.pages.components.MiniCartComponent;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class CartSteps {

    private final WebDriver driver = DriverManager.getDriver();
    private final WebDriverWait wait = DriverManager.getWait();

    private final ScenarioCartState cartState = new ScenarioCartState();

    private final HeaderComponent header = new HeaderComponent(driver, wait);
    private final PLPPage plpPage = new PLPPage(driver, wait);
    private final PDPPage pdpPage = new PDPPage(driver, wait);
    private final MiniCartComponent miniCart = new MiniCartComponent(driver, wait);
    private final CartPage cartPage = new CartPage(driver, wait);

    private BigDecimal subtotalBeforeEdit;

    // Step implementations will be added incrementally
}
