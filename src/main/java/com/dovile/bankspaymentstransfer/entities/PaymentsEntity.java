package com.dovile.bankspaymentstransfer.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
@Entity
@Table(name = "payments")
@NamedQueries({
        @NamedQuery(name = "Payments.findAll", query = "SELECT p FROM PaymentsEntity p"),
        @NamedQuery(name = "Payments.findById", query = "SELECT p FROM PaymentsEntity p WHERE p.id = :id"),
        @NamedQuery(name = "Payments.findByAmount", query = "SELECT p FROM PaymentsEntity p WHERE p.amount = :amount"),
        @NamedQuery(name = "Payments.findByDebtorIban", query = "SELECT p FROM PaymentsEntity p WHERE p.debtorIban = :debtorIban"),
        @NamedQuery(name = "Payments.findByCreditorIban", query = "SELECT p FROM PaymentsEntity p WHERE p.creditorIban = :creditorIban"),
        @NamedQuery(name = "Payments.findByAdditionalField", query = "SELECT p FROM PaymentsEntity p WHERE p.additionalField = :additionalField"),
        @NamedQuery(name = "Payments.findByStatus", query = "SELECT p FROM PaymentsEntity p WHERE p.status = :status"),
        @NamedQuery(name = "Payments.findByPaymentDate", query = "SELECT p FROM PaymentsEntity p WHERE p.paymentDate = :paymentDate")})
public class PaymentsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private BigDecimal amount;
    @Column(name = "debtor_iban")
    private String debtorIban;
    @Column(name = "creditor_iban")
    private String creditorIban;
    @Column(name = "additional_field")
    private String additionalField;
    private Boolean status;
    @Column(name = "payment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "paymentsId")
    private CancelPaymentEntity cancelPayment;
    @JoinColumn(name = "currency_data_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CurrencyDataEntity currencyDataId;
    @JoinColumn(name = "payment_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PaymentTypeEntity paymentTypeId;

    public PaymentsEntity() {
    }

    public PaymentsEntity(Integer id) {
        this.id = id;
    }

    public PaymentsEntity(Integer id, BigDecimal amount, String debtorIban, String creditorIban) {
        this.id = id;
        this.amount = amount;
        this.debtorIban = debtorIban;
        this.creditorIban = creditorIban;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public CancelPaymentEntity getCancelPayment() {
        return cancelPayment;
    }

    public void setCancelPayment(CancelPaymentEntity cancelPayment) {
        this.cancelPayment = cancelPayment;
    }

    public CurrencyDataEntity getCurrencyDataId() {
        return currencyDataId;
    }

    public void setCurrencyDataId(CurrencyDataEntity currencyDataId) {
        this.currencyDataId = currencyDataId;
    }

    public PaymentTypeEntity getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(PaymentTypeEntity paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }
}
