package com.HomeSahulat.repository;

import com.HomeSahulat.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServicesRepository extends JpaRepository<Services,Long> {
    @Modifying
    @Query("UPDATE Services s SET s.status = false WHERE s.id = :id")
    void setStatusInactive(@Param("id") Long id);

    Optional<Services> findByName(String name);
}
