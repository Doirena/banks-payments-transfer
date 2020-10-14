package com.dovile.bankspaymentstransfer.domain.response;

public class PaymentsIdResponse {
    private Integer paymentId;

    public PaymentsIdResponse(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }
}
