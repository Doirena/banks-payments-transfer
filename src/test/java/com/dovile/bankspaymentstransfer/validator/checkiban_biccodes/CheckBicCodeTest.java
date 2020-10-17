package com.dovile.bankspaymentstransfer.validator.checkiban_biccodes;

import org.junit.Test;

import static org.junit.Assert.*;

public class CheckBicCodeTest {

    @Test
    public void isBICCodeValid_true() {
        CheckBicCode bicCode = new CheckBicCode();
        Boolean isValid = bicCode.isBICCodeValid("KUSRLT24");
        assertEquals(true, isValid );
    }

    @Test
    public void isBICCodeValid_false() {
        CheckBicCode bicCode = new CheckBicCode();
        Boolean isValid = bicCode.isBICCodeValid("KU-SRLT24");
        assertEquals(false, isValid );
    }
}