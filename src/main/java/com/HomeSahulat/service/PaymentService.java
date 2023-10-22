package com.HomeSahulat.service;

import com.HomeSahulat.dto.PaymentDto;

import java.util.List;

public interface PaymentService {
    PaymentDto save(PaymentDto paymentDto);
    List<PaymentDto> getAll();
    PaymentDto findById(Long id);
    void deleteById(Long id);
    PaymentDto update(Long id, PaymentDto paymentDto);
}
