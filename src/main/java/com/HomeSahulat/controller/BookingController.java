package com.HomeSahulat.controller;

import com.HomeSahulat.dto.BookingDto;
import com.HomeSahulat.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/booking")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookingDto> createBooking(@Valid @RequestBody BookingDto bookingDto) {
        return ResponseEntity.ok(bookingService.save(bookingDto));
    }

    @GetMapping("/booking")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<BookingDto>> getAllBooking() {
        List<BookingDto> bookingDtoList = bookingService.getAll();
        return ResponseEntity.ok(bookingDtoList);
    }

    @GetMapping("/booking/by-logged-in-user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<BookingDto>> getAllBookingByLoggedInUser() {
        List<BookingDto> bookingDtoList = bookingService.getAllBookingByLoggedInUser();
        return ResponseEntity.ok(bookingDtoList);
    }

    @GetMapping("/booking/serviceProvider/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<BookingDto>> getAllBookingByServiceProvider(@PathVariable Long id) {
        List<BookingDto> bookingDtoList = bookingService.getAllBookingByServiceProvider(id);
        return ResponseEntity.ok(bookingDtoList);
    }

    @GetMapping("/booking/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Long id) {
        BookingDto bookingDto = bookingService.findById(id);
        return ResponseEntity.ok(bookingDto);
    }

    @DeleteMapping("/booking/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/booking/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookingDto> updateBooking(@PathVariable Long id,@Valid @RequestBody BookingDto bookingDto) {
        BookingDto updatedBookingDto = bookingService.update(id, bookingDto);
        return ResponseEntity.ok(updatedBookingDto);
    }
}
