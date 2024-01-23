package com.HomeSahulat.service.impl;

import com.HomeSahulat.Util.EmailUtils;
import com.HomeSahulat.Util.Helper;
import com.HomeSahulat.dto.BookingDto;
import com.HomeSahulat.exception.RecordNotFoundException;
import com.HomeSahulat.model.Booking;
import com.HomeSahulat.model.User;
import com.HomeSahulat.repository.BookingRepository;
import com.HomeSahulat.repository.ServiceProviderRepository;
import com.HomeSahulat.repository.UserRepository;
import com.HomeSahulat.service.BookingService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final EmailUtils emailUtils;
    private final Helper helper;

    public BookingServiceImpl(BookingRepository bookingRepository, UserRepository userRepository, ServiceProviderRepository serviceProviderRepository, EmailUtils emailUtils, Helper helper) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.serviceProviderRepository = serviceProviderRepository;
        this.emailUtils = emailUtils;
        this.helper = helper;
    }

    @Override
    @Transactional
    public BookingDto save(BookingDto bookingDto) {
        Booking booking = toEntity(bookingDto);
        booking.setStatus(true);
        booking.setBookingStatus("Pending");

        booking.setUser(userRepository.findById(booking.getUser().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("User not found for id => %d", booking.getUser().getId()))));
        booking.setServiceProvider(serviceProviderRepository.findById(booking.getServiceProvider().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Service Provider not found for id => %d", booking.getServiceProvider().getId()))));

        Booking createdBooking = bookingRepository.save(booking);
        emailUtils.sendEmailForBooking(createdBooking.getUser(),createdBooking.getServiceProvider(),createdBooking,"user");
        emailUtils.sendEmailForBooking(createdBooking.getUser(),createdBooking.getServiceProvider(),createdBooking,"serviceProvider");
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
    public List<BookingDto> getAllBookingByLoggedInUser() {
        User user = helper.getCurrentUser();
        List<Booking> bookingList = bookingRepository.findAllByUser_Id(user.getId());
        List<BookingDto> bookingDtoList = new ArrayList<>();

        for (Booking booking : bookingList) {
            BookingDto bookingDto = toDto(booking);
            bookingDtoList.add(bookingDto);
        }
        return bookingDtoList;
    }

    @Override
    public List<BookingDto> getAllBookingByServiceProvider(Long id, String status) {
        List<Booking> bookingList = bookingRepository.findAllByServiceProvider_IdAndBookingStatus(id, status);
        List<BookingDto> bookingDtoList = new ArrayList<>();

        for (Booking booking : bookingList) {
            BookingDto bookingDto = toDto(booking);
            bookingDtoList.add(bookingDto);
        }
        return bookingDtoList;
    }

    @Override
    public Integer countBookingsByStatus(String status) {
        List<Booking> bookingList = bookingRepository.findAllByBookingStatus(status);
        return bookingList.size();
    }

    @Override
    public Integer countNewBookings() {
        LocalDate today = LocalDate.now();

        List<Booking> bookingsCreatedToday = bookingRepository.findAllByCreatedAtBetween(
                today.atStartOfDay(), today.atTime(23, 59, 59));

        return bookingsCreatedToday.size();
    }

    @Override
    public Integer countBookingsByServiceType(String serviceType) {
        List<Booking> bookingList = bookingRepository.findAllByServiceProvider_Services_Name(serviceType);
        return bookingList.size();
    }

    @Override
    public Integer countTotalBookings() {
        List<Booking> bookingList = bookingRepository.findAll();
        return bookingList.size();
    }

    @Override
    @Transactional
    public void changeBookingStatus(Long id, String status) {
        bookingRepository.updateBookingStatus(id,status);
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
        existingBooking.setAppointmentDate(bookingDto.getAppointmentDate());
        existingBooking.setAppointmentTime(bookingDto.getAppointmentTime());
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
                .appointmentDate(booking.getAppointmentDate())
                .appointmentTime(booking.getAppointmentTime())
                .user(booking.getUser())
                .serviceProvider(booking.getServiceProvider())
                .status(booking.getStatus())
                .bookingStatus(booking.getBookingStatus())
                .build();
    }

    public Booking toEntity(BookingDto bookingDto) {
        return Booking.builder()
                .id(bookingDto.getId())
                .createdAt(bookingDto.getCreatedAt())
                .appointmentDate(bookingDto.getAppointmentDate())
                .appointmentTime(bookingDto.getAppointmentTime())
                .user(bookingDto.getUser())
                .serviceProvider(bookingDto.getServiceProvider())
                .status(bookingDto.getStatus())
                .bookingStatus(bookingDto.getBookingStatus())
                .build();
    }

}
