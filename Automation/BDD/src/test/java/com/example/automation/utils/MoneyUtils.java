package com.example.automation.utils;

import java.math.BigDecimal;

public final class MoneyUtils {

    private MoneyUtils() {
    }

    public static BigDecimal parse(String moneyText) {
        if (moneyText == null) {
            return BigDecimal.ZERO;
        }
        String normalized = moneyText.replaceAll("[^0-9.\-]", "");
        if (normalized.isBlank() || "-".equals(normalized) || ".".equals(normalized)) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(normalized);
    }
}
