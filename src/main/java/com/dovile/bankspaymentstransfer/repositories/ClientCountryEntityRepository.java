package com.dovile.bankspaymentstransfer.repositories;

import com.dovile.bankspaymentstransfer.entities.ClientCountryEntity;
import com.dovile.bankspaymentstransfer.entities.CurrencyDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
@Repository
public interface ClientCountryEntityRepository extends JpaRepository<ClientCountryEntity, Integer> {
    @Query(name = "ClientCountryEntity.findByipAddress")
    ClientCountryEntity findByipAddress(@Param("ipAddress") String ipAddress);

}
