package com.dovile.bankspaymentstransfer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
@Entity
@Table(name = "payments")
@NamedQuery(name = "PaymentsEntity.findByStatus", query = "SELECT p FROM PaymentsEntity p WHERE p.status = :status ORDER BY p.amount")
public class PaymentsEntity extends BaseEntity {

    private Double amount;
    @Column(name = "debtor_iban")
    private String debtorIban;
    @Column(name = "creditor_iban")
    private String creditorIban;
    @Column(name = "additional_field")
    private String details;
    private String bic_code;
    private Boolean status=true;
    @Column(name = "payment_date")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date paymentDate;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "payments")
    private CancelPaymentEntity cancelPayment;
    @JoinColumn(name = "currency_data_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CurrencyDataEntity currencyData;
    @JoinColumn(name = "payment_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PaymentTypeEntity paymentType;

    public PaymentsEntity() {
    }

    public PaymentsEntity(Integer id, Double amount, String debtorIban, String creditorIban,
                          String details, String bic_code, Boolean status, Date paymentDate,
                          CurrencyDataEntity currencyData, PaymentTypeEntity paymentType) {
        super(id);
        this.amount = amount;
        this.debtorIban = debtorIban;
        this.creditorIban = creditorIban;
        this.details = details;
        this.bic_code = bic_code;
        this.status = status;
        this.paymentDate = paymentDate;
        this.currencyData = currencyData;
        this.paymentType = paymentType;
    }

    public PaymentsEntity(Integer id, Double amount, Boolean status, Date paymentDate) {
        super(id);
        this.amount = amount;
        this.status = status;
        this.paymentDate = paymentDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getBic_code() {
        return bic_code;
    }

    public void setBic_code(String bic_code) {
        this.bic_code = bic_code;
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

    public CurrencyDataEntity getCurrencyData() {
        return currencyData;
    }

    public void setCurrencyData(CurrencyDataEntity currencyData) {
        this.currencyData = currencyData;
    }

    public PaymentTypeEntity getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentTypeEntity paymentType) {
        this.paymentType = paymentType;
    }
}
