package com.dovile.bankspaymentstransfer.repositories;

import com.dovile.bankspaymentstransfer.entities.ClientCountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
@Repository
public interface ClientCountryEntityRepository extends JpaRepository<ClientCountryEntity, Integer> {

}
