package com.dovile.bankspaymentstransfer.entities;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
@Entity
@Table(name = "client_country")
@NamedQuery(name = "ClientCountryEntity.findByipAddress", query = "SELECT c FROM ClientCountryEntity c WHERE c.ipAddress = :ipAddress")
public class ClientCountryEntity extends BaseEntity{

    private String ipAddress;
    private String country;

    public ClientCountryEntity(){
    }

    public ClientCountryEntity(Integer id, String ipAddress, String country) {
        super(id);
        this.ipAddress = ipAddress;
        this.country = country;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
