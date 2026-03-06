package com.example.automation.testdata;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public final class ProductCatalog {

    private static final Map<String, BigDecimal> PRICE_BY_SKU = new HashMap<>();

    static {
        PRICE_BY_SKU.put("SKU1", new BigDecimal("10"));
        PRICE_BY_SKU.put("SKU4", new BigDecimal("25"));
        // Add more SKUs/prices as needed
        PRICE_BY_SKU.put("SKU2", new BigDecimal("0"));
    }

    private ProductCatalog() {
    }

    public static BigDecimal priceOf(String sku) {
        return PRICE_BY_SKU.getOrDefault(sku.toUpperCase(), BigDecimal.ZERO);
    }
}
