package com.dovile.bankspaymentstransfer.validator.checkiban_biccodes;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public class CheckBicCode {

    private final int BIC_CODE_LENGHT_8 = 8;
    private final int BIC_CODE_LENGHT_11 = 11;

    //Is Valid
    public boolean isBICCodeValid(String bicCode) {
        if (bicCode != null) {
            bicCode = bicCode.replaceAll("\\s", "").toUpperCase(Locale.ROOT);
            if (bicCode.length() == BIC_CODE_LENGHT_8 || bicCode.length() == BIC_CODE_LENGHT_11) {
                return isOnlyLetters(bicCode);
            }
        }
        return false;
    }

    // Just letters
    private boolean isOnlyLetters(String code) {
        if (isOnlyLettersDigits(code)) {
            String newCode = code.substring(0, 4);
            return ((!newCode.equals(""))
                    && (newCode != null)
                    && (newCode.matches("^[a-zA-Z]*$")));
        }
        return false;
    }

    //Check it is letter or numbers
    private boolean isOnlyLettersDigits(String code) {
        if (checkCountryCode(code)) {
            String newCode = code.substring(6);
            return ((!newCode.equals(""))
                    && (newCode != null)
                    && (newCode.matches("^[a-zA-Z0-9]*$")));
        }
        return false;
    }

    //Check Bic code has right country code
    private boolean checkCountryCode(String code) {
        String newCode = code.substring(4, 6);
        Map<String, Integer> CountryCodeDigitSum = new HashMap<>();
        for (String countryChars : new Countrychars().getCOUNTRY_CHARS()) {
            CountryCodeDigitSum.put(countryChars.substring(0, 2), Integer.parseInt(countryChars.substring(2)));
        }
        if (!CountryCodeDigitSum.keySet().contains(newCode)) {
            return false;
        }
        return true;
    }
}
