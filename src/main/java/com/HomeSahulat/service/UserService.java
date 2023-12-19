package com.HomeSahulat.service;

import com.HomeSahulat.dto.LoginCredentials;
import com.HomeSahulat.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDto registerUser(UserDto userdto);
    Boolean checkUserVerified(LoginCredentials loginCredentials);
    Boolean checkOtpVerification(Long id, String otp);
    UserDto resendOtp(Long id);
    List<UserDto> getAll();
    UserDto findById(Long id);
    void deleteById(Long id);
    UserDto update(Long id, UserDto userDto);
    UserDto getLoggedInUser();
    void forgotPassword(String userEmail);
    void resetPassword(String userEmail, String resetCode, String newPassword);

}
