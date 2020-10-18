package com.dovile.bankspaymentstransfer.services;

import com.dovile.bankspaymentstransfer.entities.ClientCountryEntity;
import com.dovile.bankspaymentstransfer.externalwebforinfo.GetInfoFromIPFactory;
import com.dovile.bankspaymentstransfer.externalwebforinfo.InfoFromIP;
import com.dovile.bankspaymentstransfer.repositories.ClientCountryEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
@Service
public class ClientCountryServiceImpl implements ClientCountryService {

    private final static Logger logger = Logger.getLogger(ClientCountryServiceImpl.class.getName());

    @Autowired
    ClientCountryEntityRepository clientCountryEntityRepository;

    //TODO for log USER IPaddress and country
    /**
     * {@inheritDoc}
     */
    @Override
    public void saveClientCountry(String ipAddress) {
        ClientCountryEntity clientCountryEntity = new ClientCountryEntity();
        if(clientCountryEntityRepository.findByipAddress(ipAddress) == null ) {
            InfoFromIP data = new GetInfoFromIPFactory().getInfoFromIP();
            String country = data.getCountry(ipAddress);
            clientCountryEntity.setIpAddress(ipAddress);
            clientCountryEntity.setCountry(country);
            clientCountryEntityRepository.save(clientCountryEntity);
            logger.info("save new ip address and country");
        } else {
            logger.info("ip address exist");
            return;
        }
    }
}
