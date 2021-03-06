package com.dovile.bankspaymentstransfer.repositories;

import com.dovile.bankspaymentstransfer.entities.CancelPaymentEntity;
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
public interface CancelPaymentEntityRepository extends JpaRepository<CancelPaymentEntity, Integer> {
    @Query(name = "CancelPaymentEntity.findPaymentID")
    Optional<CancelPaymentEntity> findPaymentID(@Param("id") Integer id);
}
