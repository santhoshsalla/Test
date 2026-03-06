package com.example.automation.context;

import com.example.automation.testdata.ProductCatalog;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class ScenarioCartState {

    private int initialBadgeCount;
    private final Map<String, Integer> qtyBySku = new LinkedHashMap<>();

    public int getInitialBadgeCount() {
        return initialBadgeCount;
    }

    public void setInitialBadgeCount(int initialBadgeCount) {
        this.initialBadgeCount = initialBadgeCount;
    }

    public void addSku(String sku, int qtyToAdd) {
        qtyBySku.merge(sku.toUpperCase(), qtyToAdd, Integer::sum);
    }

    public void setQty(String sku, int qty) {
        qtyBySku.put(sku.toUpperCase(), qty);
    }

    public void removeSku(String sku) {
        qtyBySku.remove(sku.toUpperCase());
    }

    public int getQty(String sku) {
        return qtyBySku.getOrDefault(sku.toUpperCase(), 0);
    }

    public BigDecimal expectedSubtotal() {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (Map.Entry<String, Integer> e : qtyBySku.entrySet()) {
            subtotal = subtotal.add(ProductCatalog.priceOf(e.getKey()).multiply(BigDecimal.valueOf(e.getValue())));
        }
        return subtotal;
    }
}
