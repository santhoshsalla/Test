package com.automation.bdd.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MoneyUtils {

    private MoneyUtils() {
    }

    public static BigDecimal parse(String raw) {
        if (raw == null) {
            return BigDecimal.ZERO;
        }
        String normalized = raw.replaceAll("[^0-9.,-]", "").replace(",", "");
        if (normalized.isEmpty() || "-".equals(normalized)) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(normalized).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal multiply(BigDecimal a, int qty) {
        return a.multiply(BigDecimal.valueOf(qty)).setScale(2, RoundingMode.HALF_UP);
    }
}
