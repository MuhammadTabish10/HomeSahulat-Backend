package com.HomeSahulat.controller;

import com.HomeSahulat.dto.PaymentDto;
import com.HomeSahulat.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payment")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaymentDto> createPayment(@Valid @RequestBody PaymentDto paymentDto) {
        return ResponseEntity.ok(paymentService.save(paymentDto));
    }

    @GetMapping("/payment")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<PaymentDto>> getAllPayment() {
        List<PaymentDto> paymentDtoList = paymentService.getAll();
        return ResponseEntity.ok(paymentDtoList);
    }

    @GetMapping("/payment/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable Long id) {
        PaymentDto paymentDto = paymentService.findById(id);
        return ResponseEntity.ok(paymentDto);
    }

    @DeleteMapping("/payment/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/payment/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaymentDto> updatePayment(@PathVariable Long id,@Valid @RequestBody PaymentDto paymentDto) {
        PaymentDto updatedPaymentDto = paymentService.update(id, paymentDto);
        return ResponseEntity.ok(updatedPaymentDto);
    }
}
