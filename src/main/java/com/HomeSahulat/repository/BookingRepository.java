package com.HomeSahulat.repository;

import com.HomeSahulat.model.Booking;
import com.HomeSahulat.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    @Modifying
    @Query("UPDATE Booking b SET b.status = false WHERE b.id = :id")
    void setStatusInactive(@Param("id") Long id);
    List<Booking> findAllByUser_Id(Long id);
}
