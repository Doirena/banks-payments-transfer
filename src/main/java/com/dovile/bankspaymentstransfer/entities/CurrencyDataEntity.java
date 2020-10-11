package com.dovile.bankspaymentstransfer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
@Entity
@Table(name = "currency_data")
@NamedQueries({
        @NamedQuery(name = "CurrencyData.findAll", query = "SELECT c FROM CurrencyDataEntity c"),
        @NamedQuery(name = "CurrencyData.findById", query = "SELECT c FROM CurrencyDataEntity c WHERE c.id = :id"),
        @NamedQuery(name = "CurrencyData.findByName", query = "SELECT c FROM CurrencyDataEntity c WHERE c.name = :name"),
        @NamedQuery(name = "CurrencyData.findByRate", query = "SELECT c FROM CurrencyDataEntity c WHERE c.rate = :rate")})
public class CurrencyDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private BigDecimal rate;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "currencyDataId")
    private List<PaymentsEntity> paymentsList;

    public CurrencyDataEntity() {
    }

    public CurrencyDataEntity(Integer id) {
        this.id = id;
    }

    public CurrencyDataEntity(Integer id, String name, BigDecimal rate) {
        this.id = id;
        this.name = name;
        this.rate = rate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
