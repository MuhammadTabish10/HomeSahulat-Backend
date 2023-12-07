package com.HomeSahulat.Util;

import com.HomeSahulat.dto.CustomUserDetail;
import com.HomeSahulat.exception.RecordNotFoundException;
import com.HomeSahulat.model.User;
import com.HomeSahulat.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Helper {
    private final UserRepository userRepository;
    public Helper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static String generateRandomOTP() {
        Random random = new Random();
        int otpLength = 6;
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < otpLength; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }

    public static String formatPhoneNumber(String phoneNumber) {
        // Remove spaces from the phone number
        String formattedNumber = phoneNumber.replaceAll("\\s+", "");

        // If the number starts with "0", replace "0" with "92"
        if (formattedNumber.startsWith("0")) {
            formattedNumber = "92" + formattedNumber.substring(1);
        }

        return formattedNumber;
    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetail) {
            String phone = ((CustomUserDetail) principal).getPhone();
            return userRepository.findByPhoneAndStatusIsTrue(phone);
        } else {
            throw new RecordNotFoundException("User not Found");
        }
    }

}
