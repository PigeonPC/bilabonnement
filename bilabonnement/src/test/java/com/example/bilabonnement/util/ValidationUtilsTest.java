package com.example.bilabonnement.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilTest {

    // ---------- isNullOrNonNegativeIntegerWithMaxDigits ----------

    @Test
    void returnsTrueWhenIntegerIsNull() {
        assertTrue(ValidationUtil.isNullOrNonNegativeIntegerWithMaxDigits(null, 3));
    }

    @Test
    void returnsFalseWhenIntegerIsNegative() {
        assertFalse(ValidationUtil.isNullOrNonNegativeIntegerWithMaxDigits(-1, 3));
    }

    @Test
    void returnsTrueWhenIntegerIsWithinMaxDigits() {
        assertTrue(ValidationUtil.isNullOrNonNegativeIntegerWithMaxDigits(123, 3));   // 3 cifre, max 3
    }

    @Test
    void returnsFalseWhenIntegerExceedsMaxDigits() {
        assertFalse(ValidationUtil.isNullOrNonNegativeIntegerWithMaxDigits(1234, 3)); // 4 cifre, max 3
    }

    // ---------- isNonNegativeWithMaxDigits (BigDecimal) ----------

    @Test
    void returnsTrueWhenBigDecimalIsNull() {
        assertTrue(ValidationUtil.isNonNegativeWithMaxDigits(null, 5, 2));
    }

    @Test
    void returnsFalseWhenBigDecimalIsNegative() {
        assertFalse(ValidationUtil.isNonNegativeWithMaxDigits(new BigDecimal("-1.00"), 5, 2));
    }

    @Test
    void returnsTrueWhenBigDecimalWithinLimits() {
        BigDecimal value = new BigDecimal("12345.67"); // 5 heltal, 2 decimaler
        assertTrue(ValidationUtil.isNonNegativeWithMaxDigits(value, 5, 2));
    }

    @Test
    void returnsFalseWhenTooManyFractionDigits() {
        BigDecimal value = new BigDecimal("123.456"); // 3 decimaler
        assertFalse(ValidationUtil.isNonNegativeWithMaxDigits(value, 5, 2));
    }

    @Test
    void returnsFalseWhenTooManyIntegerDigits() {
        BigDecimal value = new BigDecimal("123456.78"); // 6 heltal
        assertFalse(ValidationUtil.isNonNegativeWithMaxDigits(value, 5, 2));
    }
}
