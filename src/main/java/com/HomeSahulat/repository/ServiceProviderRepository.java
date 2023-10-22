package com.HomeSahulat.repository;

import com.HomeSahulat.model.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider,Long> {
    @Modifying
    @Query("UPDATE ServiceProvider sp SET sp.status = false WHERE sp.id = :id")
    void setStatusInactive(@Param("id") Long id);
}
