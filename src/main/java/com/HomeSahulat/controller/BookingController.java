package com.HomeSahulat.controller;

import com.HomeSahulat.dto.BookingDto;
import com.HomeSahulat.model.Booking;
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

    @GetMapping("/booking/serviceProvider/{id}/{status}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<BookingDto>> getAllBookingByServiceProvider(@PathVariable Long id, @PathVariable String status) {
        List<BookingDto> bookingDtoList = bookingService.getAllBookingByServiceProvider(id,status);
        return ResponseEntity.ok(bookingDtoList);
    }

    @GetMapping("/booking/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Long id) {
        BookingDto bookingDto = bookingService.findById(id);
        return ResponseEntity.ok(bookingDto);
    }

    @GetMapping("/booking/by-status/{status}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Integer> getBookingCountByStatus(@PathVariable String status) {
        Integer count = bookingService.countBookingsByStatus(status);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/booking/by-service/{service}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Integer> getBookingCountByService(@PathVariable String service) {
        Integer count = bookingService.countBookingsByServiceType(service);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/booking/total")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Integer> getTotalBooking() {
        Integer count = bookingService.countTotalBookings();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/booking/new")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Integer> getNewBooking() {
        Integer count = bookingService.countNewBookings();
        return ResponseEntity.ok(count);
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

    @PutMapping("/booking/{id}/status/{status}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> updateBookingStatus(@PathVariable(name = "id") Long id,
                                                    @PathVariable(name = "status") String status) {
        bookingService.changeBookingStatus(id, status);
        return ResponseEntity.ok().build();
    }
}
