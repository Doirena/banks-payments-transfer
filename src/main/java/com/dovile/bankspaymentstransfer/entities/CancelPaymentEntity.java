package com.dovile.bankspaymentstransfer.entities;

import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.OneToOne;
import javax.persistence.NamedQuery;
import javax.persistence.TemporalType;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
@Entity
@Table(name = "cancel_payment")
@NamedQuery(name = "CancelPaymentEntity.findPaymentID", query = "SELECT c FROM CancelPaymentEntity c join c.payments p WHERE (p.id = :id)")
public class CancelPaymentEntity extends BaseEntity{

    @Column(name = "cancel_fee")
    private Double cancelFee;
    @Column(name = "payment_date")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date paymentDate;
    @JoinColumn(name = "payments_id", referencedColumnName = "id")
    @OneToOne(optional = false)
    private PaymentsEntity payments;

    public CancelPaymentEntity() {
    }

    public CancelPaymentEntity(Integer id, Double cancelFee, PaymentsEntity payments) {
        super(id);
        this.cancelFee = cancelFee;
        this.payments = payments;
    }

    public Double getCancelFee() {
        return cancelFee;
    }

    public void setCancelFee(Double cancelFee) {
        this.cancelFee = cancelFee;
    }

    public PaymentsEntity getPayments() {
        return payments;
    }

    public void setPayments(PaymentsEntity payments) {
        this.payments = payments;
    }
}
