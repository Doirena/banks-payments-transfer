package com.dovile.bankspaymentstransfer.services;

import com.dovile.bankspaymentstransfer.entities.ClientCountryEntity;
import com.dovile.bankspaymentstransfer.repositories.ClientCountryEntityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 *
 * @author barkauskaite.dovile@gmail.com
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class ClientCountryServiceImplTest {

    @InjectMocks
    private ClientCountryServiceImpl clientCountryService;

    @Mock
    private ClientCountryEntityRepository countryEntityRepository;

    @Test
    public void saveClientCountry_OK() {

        ClientCountryEntity clientCountryEntity = new ClientCountryEntity(null , "24.48.0.1","Canada");
        given(countryEntityRepository.save(clientCountryEntity)).willAnswer(invocation -> invocation.getArgument(1));
        clientCountryService.saveClientCountry(clientCountryEntity.getIpAddress());
        verify(countryEntityRepository).save(any(ClientCountryEntity.class));
    }
}