package com.HomeSahulat.dto;

import com.HomeSahulat.model.ServiceProvider;
import com.HomeSahulat.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingDto {
    private Long id;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "Appointment date should be in the future")
    private LocalDate appointmentDate;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime appointmentTime;

    private Boolean status;
    private String bookingStatus;

    @NotNull(message = "User cannot be null")
    private User user;

    @NotNull(message = "ServiceProvider cannot be null")
    private ServiceProvider serviceProvider;
}
