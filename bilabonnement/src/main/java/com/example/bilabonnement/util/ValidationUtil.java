package com.example.bilabonnement.util;

import java.math.BigDecimal;

//Hjælpemetoder til inputvalidering
//Valideringsmetoder til inputs.


// privat, tom constructor, som Forhindrer at nogen kan lave new ValidationUtil()
public final class ValidationUtil {

        private ValidationUtil() {

        }

// Tjekker: null er OK (valgfrit felt), ellers >= 0 og max N cifre

        public static boolean isNullOrNonNegativeIntegerWithMaxDigits(Integer value, int maxDigits) {
            if (value == null) return true;
            if (value < 0) return false;
            int digits = String.valueOf(Math.abs(value)).length();
            return digits <= maxDigits;
        }

// Tjekker BigDecimal: null OK, ellers >= 0, max 'intDigits' heltalscifre og max 'fracDigits' decimaler

        public static boolean isNonNegativeWithMaxDigits(BigDecimal value, int intDigits, int fracDigits) {
            if (value == null) return true;
            if (value.signum() < 0) return false;
            BigDecimal abs = value.stripTrailingZeros().abs();


            int scale = Math.max(abs.scale(), 0);
            if (scale > fracDigits) return false;

// heltalscifre = antal cifre før komma
// for BigDecimal gælder: precision = totale cifre, integerDigits ~ precision - scale (når scale >= 0)
            int precision = abs.precision();
            int integerDigits = precision - scale;
            return integerDigits <= intDigits;
        }
    }


