package com.dovile.bankspaymentstransfer.domain.request;

import com.dovile.bankspaymentstransfer.validator.IbanNumber;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */

public class PaymentsRequest {
    @NotNull(message = "Please provide a amount")
    @DecimalMin(value = "0.01", message = "Minimum amount is 0.01")
    @Digits(integer=7, fraction=2)
    private BigDecimal amount;
    @NotEmpty(message = "Please provide a debtorIban")
    @IbanNumber(message = "Bad debtorIban")
    private String debtorIban;
    @NotEmpty(message = "Please provide a creditorIban")
    @IbanNumber(message = "Bad credIban")
    private String creditorIban;
    private String additionalField;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDebtorIban() {
        return debtorIban;
    }

    public void setDebtorIban(String debtorIban) {
        this.debtorIban = debtorIban;
    }

    public String getCreditorIban() {
        return creditorIban;
    }

    public void setCreditorIban(String creditorIban) {
        this.creditorIban = creditorIban;
    }

    public String getAdditionalField() {
        return additionalField;
    }

    public void setAdditionalField(String additionalField) {
        this.additionalField = additionalField;
    }
}
