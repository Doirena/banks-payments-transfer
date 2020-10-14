package com.dovile.bankspaymentstransfer.validator.checkiban_biccodes;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public class CheckBicCode {

    private final int TYPE1_BICCODE = 8;
    private final int TYPE2_BICCODE = 11;

    //Is Valid
    public boolean isValidBicCode(String bicCode) {
        if (bicCode != null) {
            bicCode = bicCode.replaceAll("\\s", "").toUpperCase(Locale.ROOT);
            if (bicCode.length() == TYPE1_BICCODE || bicCode.length() == TYPE2_BICCODE) {
                return checkLetters(bicCode);
            }
        }
        return false;
    }

    // Just letters
    private boolean checkLetters(String code) {
        if (!checkLettersOrDigits(code)) {
            return false;
        }
        String newCode = code.substring(0, 4);
        for (int i = 0; i < newCode.length(); i++) {
            if (!Character.isLetter(newCode.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    //Check it is letter or numbers
    private boolean checkLettersOrDigits(String code) {
        if (!checkCountryCode(code)) {
            return false;
        }
        String newCode = code.substring(6);
        for (int i = 0; i < newCode.length(); i++) {
            if (!Character.isLetterOrDigit(newCode.charAt(i))) {
                return false;
            }
        }
        return true;
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
