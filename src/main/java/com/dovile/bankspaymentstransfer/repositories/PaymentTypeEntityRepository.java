package com.dovile.bankspaymentstransfer.repositories;

import com.dovile.bankspaymentstransfer.entities.PaymentTypeEntity;
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
public interface PaymentTypeEntityRepository extends JpaRepository<PaymentTypeEntity, Integer> {

    @Query(name = "PaymentTypeEntity.findByTypeName")
    Optional<PaymentTypeEntity> findByTypeName(@Param("type") String type);
}
