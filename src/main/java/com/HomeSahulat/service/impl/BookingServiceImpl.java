package com.HomeSahulat.service.impl;

import com.HomeSahulat.dto.BookingDto;
import com.HomeSahulat.exception.RecordNotFoundException;
import com.HomeSahulat.model.Booking;
import com.HomeSahulat.repository.BookingRepository;
import com.HomeSahulat.repository.ServiceProviderRepository;
import com.HomeSahulat.repository.UserRepository;
import com.HomeSahulat.service.BookingService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ServiceProviderRepository serviceProviderRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, UserRepository userRepository, ServiceProviderRepository serviceProviderRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.serviceProviderRepository = serviceProviderRepository;
    }

    @Override
    @Transactional
    public BookingDto save(BookingDto bookingDto) {
        Booking booking = toEntity(bookingDto);
        booking.setStatus(true);

        booking.setUser(userRepository.findById(booking.getUser().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("User not found for id => %d", booking.getUser().getId()))));
        booking.setServiceProvider(serviceProviderRepository.findById(booking.getServiceProvider().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Service Provider not found for id => %d", booking.getServiceProvider().getId()))));

        Booking createdBooking = bookingRepository.save(booking);
        return toDto(createdBooking);
    }

    @Override
    public List<BookingDto> getAll() {
        List<Booking> bookingList = bookingRepository.findAll();
        List<BookingDto> bookingDtoList = new ArrayList<>();

        for (Booking booking : bookingList) {
            BookingDto bookingDto = toDto(booking);
            bookingDtoList.add(bookingDto);
        }
        return bookingDtoList;
    }

    @Override
    public BookingDto findById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Booking not found for id => %d", id)));
        return toDto(booking);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Booking not found for id => %d", id)));
        bookingRepository.setStatusInactive(booking.getId());
    }

    @Override
    @Transactional
    public BookingDto update(Long id, BookingDto bookingDto) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Booking not found for id => %d", id)));

        existingBooking.setStatus(bookingDto.getStatus());
        existingBooking.setAppointmentDateTime(bookingDto.getAppointmentDateTime());
        existingBooking.setUser(userRepository.findById(bookingDto.getUser().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("User not found for id => %d", bookingDto.getUser().getId()))));
        existingBooking.setServiceProvider(serviceProviderRepository.findById(bookingDto.getServiceProvider().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Service Provider not found for id => %d", bookingDto.getServiceProvider().getId()))));

        Booking updatedBooking = bookingRepository.save(existingBooking);
        return toDto(updatedBooking);
    }


    public BookingDto toDto(Booking booking) {
        return BookingDto.builder()
                .id(booking.getId())
                .createdAt(booking.getCreatedAt())
                .appointmentDateTime(booking.getAppointmentDateTime())
                .user(booking.getUser())
                .serviceProvider(booking.getServiceProvider())
                .status(booking.getStatus())
                .build();
    }

    public Booking toEntity(BookingDto bookingDto) {
        return Booking.builder()
                .id(bookingDto.getId())
                .createdAt(bookingDto.getCreatedAt())
                .appointmentDateTime(bookingDto.getAppointmentDateTime())
                .user(bookingDto.getUser())
                .serviceProvider(bookingDto.getServiceProvider())
                .status(bookingDto.getStatus())
                .build();
    }

}
