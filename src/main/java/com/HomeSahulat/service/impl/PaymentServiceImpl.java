package com.HomeSahulat.service.impl;

import com.HomeSahulat.dto.PaymentDto;
import com.HomeSahulat.exception.RecordNotFoundException;
import com.HomeSahulat.model.Payment;
import com.HomeSahulat.repository.BookingRepository;
import com.HomeSahulat.repository.PaymentRepository;
import com.HomeSahulat.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    @Transactional
    public PaymentDto save(PaymentDto paymentDto) {
        Payment payment = toEntity(paymentDto);
        payment.setStatus(true);

        payment.setBooking(bookingRepository.findById(paymentDto.getBooking().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("User not found for id => %d", payment.getBooking().getId()))));

        Payment createdPayment = paymentRepository.save(payment);
        return toDto(createdPayment);
    }

    @Override
    public List<PaymentDto> getAll() {
        List<Payment> paymentList = paymentRepository.findAll();
        List<PaymentDto> paymentDtoList = new ArrayList<>();

        for (Payment payment : paymentList) {
            PaymentDto paymentDto = toDto(payment);
            paymentDtoList.add(paymentDto);
        }
        return paymentDtoList;
    }

    @Override
    public PaymentDto findById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Payment not found for id => %d", id)));
        return toDto(payment);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Payment not found for id => %d", id)));
        paymentRepository.setStatusInactive(payment.getId());
    }

    @Override
    @Transactional
    public PaymentDto update(Long id, PaymentDto paymentDto) {
        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Payment not found for id => %d", id)));

        existingPayment.setStatus(paymentDto.getStatus());
        existingPayment.setAmount(paymentDto.getAmount());
        existingPayment.setBooking(bookingRepository.findById(paymentDto.getBooking().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("User not found for id => %d", paymentDto.getBooking().getId()))));

        Payment updatedPayment = paymentRepository.save(existingPayment);
        return toDto(updatedPayment);
    }

    public PaymentDto toDto(Payment payment) {
        return PaymentDto.builder()
                .id(payment.getId())
                .createdAt(payment.getCreatedAt())
                .amount(payment.getAmount())
                .booking(payment.getBooking())
                .status(payment.getStatus())
                .build();
    }

    public Payment toEntity(PaymentDto paymentDto) {
        return Payment.builder()
                .id(paymentDto.getId())
                .createdAt(paymentDto.getCreatedAt())
                .amount(paymentDto.getAmount())
                .booking(paymentDto.getBooking())
                .status(paymentDto.getStatus())
                .build();
    }
}
