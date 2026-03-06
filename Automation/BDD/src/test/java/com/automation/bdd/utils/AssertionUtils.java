package com.automation.bdd.utils;

import org.assertj.core.api.Assertions;

import java.math.BigDecimal;

public final class AssertionUtils {

    private AssertionUtils() {
    }

    public static void assertMoneyEquals(BigDecimal expected, BigDecimal actual, String reason) {
        Assertions.assertThat(actual)
                .as(reason)
                .isNotNull();

        Assertions.assertThat(actual.compareTo(expected) == 0)
                .as(reason + " | expected=" + expected + " actual=" + actual)
                .isTrue();
    }
}
