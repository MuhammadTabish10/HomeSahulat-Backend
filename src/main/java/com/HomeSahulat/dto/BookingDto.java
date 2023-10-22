package com.HomeSahulat.dto;

import com.HomeSahulat.model.ServiceProvider;
import com.HomeSahulat.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingDto {
    private Long id;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Appointment date should be in the future")
    private LocalDate appointmentDate;

    private Boolean status;

    @NotNull(message = "User cannot be null")
    private User user;

    @NotNull(message = "ServiceProvider cannot be null")
    private ServiceProvider serviceProvider;
}
