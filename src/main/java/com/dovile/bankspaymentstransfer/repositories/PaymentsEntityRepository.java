package com.dovile.bankspaymentstransfer.repositories;

import com.dovile.bankspaymentstransfer.entities.PaymentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
@Repository
public interface PaymentsEntityRepository extends JpaRepository<PaymentsEntity, Integer> {
    @Query(name = "PaymentsEntity.findByStatus")
    List<PaymentsEntity> findByStatus(@Param("status") Boolean status);
}
