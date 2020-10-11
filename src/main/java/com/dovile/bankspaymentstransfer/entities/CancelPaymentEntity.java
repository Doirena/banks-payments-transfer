package com.dovile.bankspaymentstransfer.entities;

import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.OneToOne;
import javax.persistence.TemporalType;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
@Entity
@Table(name = "cancel_payment")
public class CancelPaymentEntity extends BaseEntity{

    @Column(name = "cancel_fee")
    private BigDecimal cancelFee;
    @Column(name = "payment_date")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date paymentDate;
    @JoinColumn(name = "payments_id", referencedColumnName = "id")
    @OneToOne(optional = false)
    private PaymentsEntity payments;

    public CancelPaymentEntity() {
    }

    public BigDecimal getCancelFee() {
        return cancelFee;
    }

    public void setCancelFee(BigDecimal cancelFee) {
        this.cancelFee = cancelFee;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentsEntity getPayments() {
        return payments;
    }

    public void setPayments(PaymentsEntity payments) {
        this.payments = payments;
    }
}
