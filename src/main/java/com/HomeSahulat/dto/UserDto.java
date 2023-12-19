package com.HomeSahulat.dto;

import com.HomeSahulat.model.Location;
import com.HomeSahulat.model.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private String name;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    private String email;

    private String firstName;
    private String lastName;

    @NotBlank(message = "Phone cannot be blank")
    @Pattern(regexp = "\\d{11}", message = "Invalid phone number")
    private String phone;

    private String profilePictureUrl;
    private String deviceId;
    private String otp;
    private Boolean otpFlag;
    private String resetCode;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime resetCodeTimestamp;

    private Location location;
    private Boolean status;
    private Set<Role> roles = new HashSet<>();
}
