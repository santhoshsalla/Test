package com.test.automation.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/cart_tc200xx.feature",
        glue = {"com.test.automation.steps"},
        plugin = {"pretty", "html:target/cucumber-report/cart-tc200xx.html"},
        monochrome = true
)
public class CartTc200xxTestRunner {
}
