package com.dovile.bankspaymentstransfer.validator;

/**
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public enum TypeEnum {
    TYPE1("EUR"),
    TYPE2("USD"),
    TYPE3("EUR","USD");

    private String typeCurrency;
    private String typeThreeCurrency;

    TypeEnum(String typeCurrency) {
        this.typeCurrency = typeCurrency;
    }

    TypeEnum(String typeCurrency, String typeThreeCurrency) {
        this.typeCurrency = typeCurrency;
        this.typeThreeCurrency = typeThreeCurrency;
    }

    public String getTypeCurrency() {
        return typeCurrency;
    }

    public String getTypeThreeCurrency() {
        return typeThreeCurrency;
    }
}
