package com.HomeSahulat.dto;

import com.HomeSahulat.model.ServiceProvider;
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
public class ReviewDto {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @NotBlank(message = "Note cannot be blank")
    private String note;

    @NotNull(message = "Rating cannot be null")
    @PositiveOrZero(message = "Rating must be a positive number or zero")
    private Double rating;

    private Boolean status;

    @NotNull(message = "Service provider cannot be null")
    private ServiceProvider serviceProvider;

    @NotNull(message = "User cannot be null")
    private User user;
}
