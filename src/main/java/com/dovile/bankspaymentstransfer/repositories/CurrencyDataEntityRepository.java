package com.dovile.bankspaymentstransfer.repositories;

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
public interface CurrencyDataEntityRepository extends JpaRepository<CurrencyDataEntity, Integer> {
    @Query(name = "CurrencyDataEntity.findByName")
    Optional<CurrencyDataEntity> findByName(@Param("currency") String currency);
}
