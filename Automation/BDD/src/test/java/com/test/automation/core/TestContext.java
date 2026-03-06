package com.test.automation.core;

import java.math.BigDecimal;

public class TestContext {

    public int baselineBadgeCount;
    public BigDecimal baselineMiniCartTotal;
    public BigDecimal baselineCartTotal;

    public String lastAddedSku;
    public String lastRemovedSku;

    public static final ThreadLocal<TestContext> CTX = ThreadLocal.withInitial(TestContext::new);

    public static TestContext get() {
        return CTX.get();
    }

    public static void reset() {
        CTX.remove();
    }
}
