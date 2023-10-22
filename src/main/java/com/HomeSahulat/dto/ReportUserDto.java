package com.HomeSahulat.dto;

import com.HomeSahulat.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportUserDto {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @NotBlank(message = "Note cannot be blank")
    private String note;

    private Boolean status;

    @NotNull(message = "Report from user cannot be null")
    private User reportFromUser;

    @NotNull(message = "Report to user cannot be null")
    private User reportToUser;
}
