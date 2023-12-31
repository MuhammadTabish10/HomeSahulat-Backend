package com.HomeSahulat.repository;

import com.HomeSahulat.model.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider,Long> {
    @Modifying
    @Query("UPDATE ServiceProvider sp SET sp.status = false WHERE sp.id = :id")
    void setStatusInactive(@Param("id") Long id);

    @Query("SELECT sp FROM ServiceProvider sp JOIN sp.services s WHERE s.name = :serviceName AND sp.status = true")
    List<ServiceProvider> findByServiceNameAndStatusTrue(@Param("serviceName") String serviceName);

    List<ServiceProvider> findAllByVerified(Boolean verify);

    Optional<ServiceProvider> findByUser_Id(Long id);

    @Modifying
    @Query("UPDATE ServiceProvider sp SET sp.verified = :verify WHERE sp.id = :id")
    void setVerified(@Param("verify") Boolean verify,@Param("id") Long id);
}
