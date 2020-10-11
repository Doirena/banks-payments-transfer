package com.dovile.bankspaymentstransfer.entities;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@NamedQueries({
        @NamedQuery(name = "CancelPayment.findAll", query = "SELECT c FROM CancelPaymentEntity c"),
        @NamedQuery(name = "CancelPayment.findById", query = "SELECT c FROM CancelPaymentEntity c WHERE c.id = :id"),
        @NamedQuery(name = "CancelPayment.findByAmount", query = "SELECT c FROM CancelPaymentEntity c WHERE c.amount = :amount"),
        @NamedQuery(name = "CancelPayment.findByPaymentDate", query = "SELECT c FROM CancelPaymentEntity c WHERE c.paymentDate = :paymentDate")})
public class CancelPaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private BigDecimal amount;
    @Column(name = "payment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;
    @JoinColumn(name = "payments_id", referencedColumnName = "id")
    @OneToOne(optional = false)
    private PaymentsEntity paymentsId;

    public CancelPaymentEntity() {
    }

    public CancelPaymentEntity(Integer id) {
        this.id = id;
    }

    public CancelPaymentEntity(Integer id, BigDecimal amount) {
        this.id = id;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentsEntity getPaymentsId() {
        return paymentsId;
    }

    public void setPaymentsId(PaymentsEntity paymentsId) {
        this.paymentsId = paymentsId;
    }
}
