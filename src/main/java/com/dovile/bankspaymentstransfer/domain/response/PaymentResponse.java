package com.dovile.bankspaymentstransfer.domain.response;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public class PaymentResponse extends PaymentsIdResponse {

    private Double amount;
    private String debtorIban;
    private String creditorIban;
    private String details;
    private String bic_code;

    public PaymentResponse(Integer paymentId, Double amount, String debtorIban, String creditorIban, String details, String bic_code) {
        super(paymentId);
        this.amount = amount;
        this.debtorIban = debtorIban;
        this.creditorIban = creditorIban;
        this.details = details;
        this.bic_code = bic_code;
    }

    public Double getAmount() {
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
