package com.HomeSahulat.dto;

import com.HomeSahulat.model.Services;
import com.HomeSahulat.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceProviderDto {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotBlank(message = "Cnic cannot be blank")
    private String cnicNo;

    @NotNull(message = "Hourly price cannot be null")
    @PositiveOrZero(message = "Hourly price must be a positive number or zero")
    private Double hourlyPrice;

    @NotNull(message = "Total experience cannot be null")
    @PositiveOrZero(message = "Total experience must be a positive number or zero")
    private Double totalExperience;

    @PositiveOrZero(message = "Total rating must be a positive number or zero")
    private Double totalRating;

    private Boolean atWork;

    @NotNull(message = "Shop status cannot be null")
    private Boolean haveShop;

    private String cnicUrl;
    private Boolean status;
    private Boolean verified;

    @NotNull(message = "User cannot be null")
    private User user;

    @NotNull(message = "Service cannot be null")
    private Services services;

}
