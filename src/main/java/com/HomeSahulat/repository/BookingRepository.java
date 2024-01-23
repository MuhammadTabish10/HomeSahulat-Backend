package com.HomeSahulat.repository;

import com.HomeSahulat.model.Booking;
import com.HomeSahulat.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    @Modifying
    @Query("UPDATE Booking b SET b.status = false WHERE b.id = :id")
    void setStatusInactive(@Param("id") Long id);
    List<Booking> findAllByUser_Id(Long id);
    List<Booking> findAllByServiceProvider_IdAndBookingStatus(Long id, String status);

    List<Booking> findAllByBookingStatus(String status);
    List<Booking> findAllByCreatedAtBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<Booking> findAllByServiceProvider_Services_Name(String serviceType);

    @Modifying
    @Query("UPDATE Booking b SET b.bookingStatus = :status WHERE b.id = :bookingId")
    void updateBookingStatus(@Param("bookingId") Long bookingId, @Param("status") String status);
}

