package com.dovile.bankspaymentstransfer.domain.response;
import java.math.BigDecimal;

public class CancelPaymentResponse extends PaymentsIdResponse {

    private BigDecimal cancelFee;

    public CancelPaymentResponse(Integer paymentId, BigDecimal cancelFee) {
        super(paymentId);
        this.cancelFee = cancelFee;
    }

    public BigDecimal getCancelFee() {
        return cancelFee;
    }

}
