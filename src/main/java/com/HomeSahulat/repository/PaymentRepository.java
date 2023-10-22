package com.HomeSahulat.repository;

import com.HomeSahulat.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    @Modifying
    @Query("UPDATE Payment p SET p.status = false WHERE p.id = :id")
    void setStatusInactive(@Param("id") Long id);
}
