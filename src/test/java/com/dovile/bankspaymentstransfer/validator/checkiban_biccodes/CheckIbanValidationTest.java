package com.dovile.bankspaymentstransfer.validator.checkiban_biccodes;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CheckIbanValidationTest {

    @Test
    public void give_right_iban_should_return_true() {
        CheckIbanValidation iban = new CheckIbanValidation();
        Boolean result = iban.isIBANValid("LT647044001231465456");
        assertEquals(true, result);
    }
    @Test
    public void give_bad_iban_should_return_false() {
        CheckIbanValidation iban = new CheckIbanValidation();
        Boolean result = iban.isIBANValid("BBBBBB");
        assertEquals(false, result);
    }
}