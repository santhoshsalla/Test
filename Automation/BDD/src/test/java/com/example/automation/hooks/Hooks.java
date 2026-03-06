package com.example.automation.hooks;

import com.example.automation.core.BaseTest;
import com.example.automation.core.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks extends BaseTest {

    @Before
    public void beforeScenario() {
        setUp();
        DriverManager.set(driver, wait, config);
    }

    @After
    public void afterScenario() {
        try {
            tearDown();
        } finally {
            DriverManager.clear();
        }
    }
}
