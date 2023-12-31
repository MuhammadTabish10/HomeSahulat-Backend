package com.HomeSahulat.service;

import com.HomeSahulat.dto.BookingDto;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface BookingService {
    BookingDto save(BookingDto bookingDto);
    List<BookingDto> getAll();
    List<BookingDto> getAllBookingByLoggedInUser();
    List<BookingDto> getAllBookingByServiceProvider(Long id);
    Integer countBookingsByStatus(String status);
    Integer countNewBookings();
    Integer countBookingsByServiceType(String ServiceType);
    Integer countTotalBookings();
    BookingDto findById(Long id);
    void deleteById(Long id);
    BookingDto update(Long id, BookingDto bookingDto);
}
