package com.dovile.bankspaymentstransfer.services;

public interface ClientCountryService {
    /**
     * This method is separated because there isn't any business logic with main service.
     * If main service will broke down this service will work.
     * This method will find country by client id and save to db.
     * @param ipAddress
     */
    void saveClientCountry(String ipAddress);
}
