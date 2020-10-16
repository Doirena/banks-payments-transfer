package com.dovile.bankspaymentstransfer.validator.checkiban_biccodes;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public class CheckIbanValidation {

    private static final BigInteger BIG_INTEGER_97 = new BigInteger("97");
    private final int EXPECTED_MOD_97 = 1;
    private final int IBANNUMBER_MIN_SIZE = 15;
    private final int IBANNUMBER_MAX_SIZE = 34;

    //Return true or false of IBAN
    public boolean isIBANValid(String iban) {
        if (iban != null) {
            iban = iban.replaceAll("\\s", "").toUpperCase(Locale.ROOT);
            if (checkIbanLenght(iban)) {
                BigInteger integerValue = stringConvertToInteger(iban);
                return integerValue.mod(BIG_INTEGER_97).intValue() == EXPECTED_MOD_97;
            }
        }
        return false;
    }

    //1. Construct new IBAN no, Country code and chars move to the end
    //2. Return new IBAN for BigInteger type
    private BigInteger stringConvertToInteger(String iban) {
        String newIban = iban.substring(4) + iban.substring(0, 4);
        return new BigInteger(replaceCountryCharactersWithDigits(newIban));
    }

    //Make IBAN no full digits
    private String replaceCountryCharactersWithDigits(String iban) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iban.length(); i++) {
            sb.append(Character.digit(iban.charAt(i), 36));
        }
        return sb.toString();
    }

    //Check IBAN lenght if its correct by some criteria
    private boolean checkIbanLenght(String iban) {
        Map<String, Integer> CountryCodeDigitSum = new HashMap<>();

        for (String countryChars : new Countrychars().getCOUNTRY_CHARS()) {
            CountryCodeDigitSum.put(countryChars.substring(0, 2), Integer.parseInt(countryChars.substring(2)));
        }
        if (iban.length() > IBANNUMBER_MAX_SIZE
                || iban.length() < IBANNUMBER_MIN_SIZE
                || !iban.matches("[0-9A-Z]+")
                || CountryCodeDigitSum.getOrDefault(iban.substring(0, 2), 0) != iban.length()) {
            return false;
        }
        return true;
    }
}
