package com.dovile.bankspaymentstransfer.validator.checkiban_biccodes;

import org.junit.Test;

import static org.junit.Assert.*;

public class CheckBicCodeTest {

    @Test
    public void give_Right_Bic_code_Should_Return_true() {
        CheckBicCode bicCode = new CheckBicCode();
        Boolean isValid = bicCode.isBICCodeValid("KUSRLT24");
        assertEquals(true, isValid );
    }

    @Test
    public void give_Bad_Bic_code_Should_Return_false() {
        CheckBicCode bicCode = new CheckBicCode();
        Boolean isValid = bicCode.isBICCodeValid("KU-SRLT24");
        assertEquals(false, isValid );
    }
}