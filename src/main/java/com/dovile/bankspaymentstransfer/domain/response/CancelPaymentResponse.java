package com.dovile.bankspaymentstransfer.domain.response;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public class CancelPaymentResponse extends PaymentsIdResponse {

    private Double cancelFee;

    public CancelPaymentResponse(Integer paymentId, Double cancelFee) {
        super(paymentId);
        this.cancelFee = cancelFee;
    }

    public Double getCancelFee() {
        return cancelFee;
    }

    public void setCancelFee(Double cancelFee) {
        this.cancelFee = cancelFee;
    }
}
