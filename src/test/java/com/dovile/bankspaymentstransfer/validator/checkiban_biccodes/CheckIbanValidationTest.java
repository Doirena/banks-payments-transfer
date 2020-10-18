package com.dovile.bankspaymentstransfer.validator.checkiban_biccodes;

import org.junit.Test;

import static org.junit.Assert.*;

public class CheckIbanValidationTest {

    @Test
    public void give_Right_Iban_Should_Return_true() {
        CheckIbanValidation iban = new CheckIbanValidation();
        Boolean result = iban.isIBANValid("LT647044001231465456");
        assertEquals(true, result);
    }
    @Test
    public void give_Bad_Iban_Should_Return_false() {
        CheckIbanValidation iban = new CheckIbanValidation();
        Boolean result = iban.isIBANValid("BBBBBB");
        assertEquals(false, result);
    }
}