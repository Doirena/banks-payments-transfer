package com.dovile.bankspaymentstransfer.domain.request;

import javax.validation.constraints.NotEmpty;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public class PaymentsRequest {
    private String amount;
    @NotEmpty(message = "Please fill in the debtorIban field")
    private String debtorIban;
    @NotEmpty(message = "Please fill in the creditorIban field")
    private String creditorIban;
    private String details;
    private String bic_code;

    public PaymentsRequest(String amount,String debtorIban, String creditorIban, String details, String bic_code) {
        this.amount = amount;
        this.debtorIban = debtorIban;
        this.creditorIban = creditorIban;
        this.details = details;
        this.bic_code = bic_code;
    }

    public String getAmount() {
        return amount;
    }

    public String getDebtorIban() {
        return debtorIban;
    }

    public String getCreditorIban() {
        return creditorIban;
    }

    public String getDetails() {
        return details;
    }

    public String getBic_code() {
        return bic_code;
    }
}
