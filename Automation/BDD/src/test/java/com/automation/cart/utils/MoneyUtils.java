package com.automation.cart.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MoneyUtils {

    private MoneyUtils() {
    }

    public static BigDecimal parseCurrency(String text) {
        if (text == null) {
            return BigDecimal.ZERO;
        }
        // Keep digits, dot and minus. Supports formats like "$1,234.56".
        String normalized = text.replaceAll("[^0-9.\-]", "");
        if (normalized.isBlank() || "-".equals(normalized) || ".".equals(normalized)) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(normalized).setScale(2, RoundingMode.HALF_UP);
    }
}
