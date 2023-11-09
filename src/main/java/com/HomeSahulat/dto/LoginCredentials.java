package com.HomeSahulat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginCredentials {
    @NotBlank(message = "PhoneNumber cannot be blank")
    private String phone;

    @NotBlank(message = "Password cannot be blank")
    private String password;
}
