package com.dovile.bankspaymentstransfer.validator.checkiban_biccodes;

import org.junit.Test;

import static org.junit.Assert.*;

public class CheckIbanValidationTest {

    @Test
    public void isIBANValid_true() {
        CheckIbanValidation iban = new CheckIbanValidation();
        Boolean result = iban.isIBANValid("LT647044001231465456");
        assertEquals(true, result);
    }
    @Test
    public void isIBANValid_false() {
        CheckIbanValidation iban = new CheckIbanValidation();
        Boolean result = iban.isIBANValid("BBBBBB");
        assertEquals(false, result);
    }
}