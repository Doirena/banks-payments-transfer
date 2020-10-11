package com.dovile.bankspaymentstransfer.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
@Entity
@Table(name = "payment_type")
@NamedQueries({
        @NamedQuery(name = "PaymentType.findAll", query = "SELECT p FROM PaymentTypeEntity p"),
        @NamedQuery(name = "PaymentType.findById", query = "SELECT p FROM PaymentTypeEntity p WHERE p.id = :id"),
        @NamedQuery(name = "PaymentType.findByTypeName", query = "SELECT p FROM PaymentTypeEntity p WHERE p.typeName = :typeName"),
        @NamedQuery(name = "PaymentType.findByCoefficient", query = "SELECT p FROM PaymentTypeEntity p WHERE p.coefficient = :coefficient")})
public class PaymentTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "type_name")
    private String typeName;
    private BigDecimal coefficient;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paymentTypeId")
    private List<PaymentsEntity> paymentsList;

    public PaymentTypeEntity() {
    }

    public PaymentTypeEntity(Integer id) {
        this.id = id;
    }

    public PaymentTypeEntity(Integer id, String typeName, BigDecimal coefficient) {
        this.id = id;
        this.typeName = typeName;
        this.coefficient = coefficient;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public BigDecimal getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(BigDecimal coefficient) {
        this.coefficient = coefficient;
    }

    public List<PaymentsEntity> getPaymentsList() {
        return paymentsList;
    }

    public void setPaymentsList(List<PaymentsEntity> paymentsList) {
        this.paymentsList = paymentsList;
    }
}
