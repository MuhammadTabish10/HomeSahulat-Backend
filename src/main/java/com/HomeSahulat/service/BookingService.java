package com.HomeSahulat.service;

import com.HomeSahulat.dto.BookingDto;

import java.util.List;

public interface BookingService {
    BookingDto save(BookingDto bookingDto);
    List<BookingDto> getAll();
    BookingDto findById(Long id);
    void deleteById(Long id);
    BookingDto update(Long id, BookingDto bookingDto);
}
