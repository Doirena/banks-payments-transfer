package com.dovile.bankspaymentstransfer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
@Entity
@Table(name = "payment_type")
@NamedQuery(name = "PaymentTypeEntity.findByTypeName", query = "SELECT p FROM PaymentTypeEntity p WHERE p.typeName = :type")

public class PaymentTypeEntity extends BaseEntity {

    @Column(name = "type_name")
    private String typeName;
    private Double coefficient;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paymentType")
    private List<PaymentsEntity> paymentsList;

    public PaymentTypeEntity() {
    }

    public PaymentTypeEntity(Integer id, String typeName, Double coefficient) {
        super(id);
        this.typeName = typeName;
        this.coefficient = coefficient;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    public List<PaymentsEntity> getPaymentsList() {
        return paymentsList;
    }

    public void setPaymentsList(List<PaymentsEntity> paymentsList) {
        this.paymentsList = paymentsList;
    }
}
