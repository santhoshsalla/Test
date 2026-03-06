package com.test.automation.steps;

import com.test.automation.core.BaseTest;
import com.test.automation.core.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks extends BaseTest {

    @Before(order = 0)
    public void beforeScenario() {
        setUp();
        // Ensure thread-local context is initialized
        TestContext.get();
    }

    @After(order = 0)
    public void afterScenario() {
        try {
            tearDown();
        } finally {
            TestContext.reset();
        }
    }
}
