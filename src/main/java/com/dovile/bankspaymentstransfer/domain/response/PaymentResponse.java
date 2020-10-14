package com.dovile.bankspaymentstransfer.domain.response;

import java.math.BigDecimal;

public class PaymentResponse extends PaymentsIdResponse {

    private BigDecimal amount;
    private String debtorIban;
    private String creditorIban;
    private String additionalField;

    public PaymentResponse(Integer paymentId, BigDecimal amount, String debtorIban, String creditorIban, String additionalField) {
        super(paymentId);
        this.amount = amount;
        this.debtorIban = debtorIban;
        this.creditorIban = creditorIban;
        this.additionalField = additionalField;
    }

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
