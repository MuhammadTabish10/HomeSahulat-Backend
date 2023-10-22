package com.HomeSahulat.dto;

import com.HomeSahulat.model.Booking;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDto {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @NotNull(message = "Amount cannot be null")
    @PositiveOrZero(message = "Amount must be a positive number or zero")
    private Double amount;

    private Boolean status;

    @NotNull(message = "Booking cannot be null")
    private Booking booking;
}
