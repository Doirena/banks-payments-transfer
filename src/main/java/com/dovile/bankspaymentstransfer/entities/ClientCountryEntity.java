package com.dovile.bankspaymentstransfer.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "client_country")
public class ClientCountryEntity extends BaseEntity{

    private String ipAddress;
    private String ccountry;

    public ClientCountryEntity(String ipAddress, String ccountry) {
        this.ipAddress = ipAddress;
        this.ccountry = ccountry;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCcountry() {
        return ccountry;
    }

    public void setCcountry(String ccountry) {
        this.ccountry = ccountry;
    }
}
