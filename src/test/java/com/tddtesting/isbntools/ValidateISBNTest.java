package com.tddtesting.isbntools;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidateISBNTest {

    @Test
    public void checkAValid10DigitISBN() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("0140449116");
        assertTrue("first value", result);
        result = validator.checkISBN("9781853267338");
        assertTrue("second value", result);
    }

    @Test
    public void checkAValid13DigitsISBN() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("9783507697706");
        assertTrue("first value", result);
        result = validator.checkISBN("0140177396");
        assertTrue("second value", result);
    }
    @Test
    public void ISBNNumbersEndingInAXAreValid() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("012000030X");
        assertTrue(result);
    }

    @Test
    public void checkAnInvalid10DigitISBN() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("0140449117");
        assertFalse(result);
    }

    @Test
    public void checkAnInvalid13DigitISBN() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("9783507697700");
        assertFalse(result);
    }

    @Test(expected = NumberFormatException.class)
    public void nineDigitISBNsAreNotAllowed() {
        ValidateISBN validator = new ValidateISBN();
        validator.checkISBN("123456789");
    }

    @Test(expected = NumberFormatException.class)
    public void NonNumericISBNsAreNotAllowed() {
        ValidateISBN validator = new ValidateISBN();
        validator.checkISBN("hellojunit");
    }
}
