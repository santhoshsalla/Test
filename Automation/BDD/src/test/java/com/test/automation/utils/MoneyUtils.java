package com.test.automation.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MoneyUtils {

    private MoneyUtils() {
    }

    public static BigDecimal parseMoney(String raw) {
        if (raw == null) {
            return BigDecimal.ZERO;
        }
        // Keeps digits, minus sign and decimal separator.
        String normalized = raw.replaceAll("[^0-9\\.-]", "");
        if (normalized.isBlank() || "-".equals(normalized) || ".".equals(normalized)) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(normalized).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal multiply(BigDecimal amount, int qty) {
        return amount.multiply(BigDecimal.valueOf(qty)).setScale(2, RoundingMode.HALF_UP);
    }
}
