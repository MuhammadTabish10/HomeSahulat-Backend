package com.HomeSahulat.repository;

import com.HomeSahulat.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    @Modifying
    @Query("UPDATE Review r SET r.status = false WHERE r.id = :id")
    void setStatusInactive(@Param("id") Long id);
}
