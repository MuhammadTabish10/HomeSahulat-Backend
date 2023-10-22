package com.HomeSahulat.repository;

import com.HomeSahulat.model.ReportUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportUserRepository extends JpaRepository<ReportUser,Long> {
    @Modifying
    @Query("UPDATE ReportUser ru SET ru.status = false WHERE ru.id = :id")
    void setStatusInactive(@Param("id") Long id);
}
