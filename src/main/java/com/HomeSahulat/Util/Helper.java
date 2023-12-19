package com.HomeSahulat.Util;

import com.HomeSahulat.dto.CustomUserDetail;
import com.HomeSahulat.exception.RecordNotFoundException;
import com.HomeSahulat.model.User;
import com.HomeSahulat.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

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

    public boolean isValidResetCode(User user, String resetCode) {
        if (user.getResetCode() == null || !user.getResetCode().equals(resetCode)) {
            return false;
        }
        LocalDateTime resetCodeTimestamp = user.getResetCodeTimestamp();
        LocalDateTime currentTimestamp = LocalDateTime.now();
        Duration duration = Duration.between(resetCodeTimestamp, currentTimestamp);
        long expirationTimeInMinutes = 60;
        return duration.toMinutes() <= expirationTimeInMinutes;
    }

    public String generateResetCode() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 6);
    }
}
