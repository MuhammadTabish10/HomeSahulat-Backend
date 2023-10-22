package com.HomeSahulat.dto;

import com.HomeSahulat.model.ServiceProvider;
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
public class AttachmentDto {
    private Long id;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "File URL cannot be blank")
    private String fileUrl;

    private Boolean status;

    @NotNull(message = "ServiceProvider cannot be null")
    private ServiceProvider serviceProvider;
}
