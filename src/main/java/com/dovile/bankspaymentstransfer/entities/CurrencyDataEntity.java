package com.dovile.bankspaymentstransfer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
@Entity
@Table(name = "currency_data")
@NamedQuery(name = "CurrencyDataEntity.findByName", query = "SELECT c FROM CurrencyDataEntity c WHERE c.name = :currency")

public class CurrencyDataEntity extends BaseEntity {

    private String name;
    private BigDecimal rate;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "currencyData")
    private List<PaymentsEntity> paymentsList;

    public CurrencyDataEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public List<PaymentsEntity> getPaymentsList() {
        return paymentsList;
    }

    public void setPaymentsList(List<PaymentsEntity> paymentsList) {
        this.paymentsList = paymentsList;
    }
}
