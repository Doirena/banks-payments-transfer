package com.dovile.bankspaymentstransfer.validator.checkiban_biccodes;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CheckBicCodeTest {

    @Test
    public void give_right_bic_code_should_return_true() {
        CheckBicCode bicCode = new CheckBicCode();
        Boolean isValid = bicCode.isBICCodeValid("KUSRLT24");
        assertEquals(true, isValid );
    }

    @Test
    public void give_bad_bic_code_should_return_false() {
        CheckBicCode bicCode = new CheckBicCode();
        Boolean isValid = bicCode.isBICCodeValid("KU-SRLT24");
        assertEquals(false, isValid );
    }
}