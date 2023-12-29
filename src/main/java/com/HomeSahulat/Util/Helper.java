package com.HomeSahulat.Util;

import com.HomeSahulat.dto.CustomUserDetail;
import com.HomeSahulat.exception.RecordNotFoundException;
import com.HomeSahulat.model.User;
import com.HomeSahulat.repository.UserRepository;
import com.HomeSahulat.service.BucketService;
import com.HomeSahulat.service.impl.bucketServiceImpl;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

@Component
public class Helper {
    private final UserRepository userRepository;
    private final BucketService bucketService;
    private static final Logger logger = LoggerFactory.getLogger(bucketServiceImpl.class);

    public Helper(UserRepository userRepository, BucketService bucketService) {
        this.userRepository = userRepository;
        this.bucketService = bucketService;
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
            return userRepository.findByPhoneAndStatusIsTrue(phone)
                    .orElseThrow(() -> new RecordNotFoundException("User not found"));
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

    public String saveCnicToS3(MultipartFile file, String folderName) {
        try {
            String filename = "Cnic";

            // Extract file extension using FilenameUtils
            String fileExtension = "." + FilenameUtils.getExtension(file.getOriginalFilename());

            // Generate timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"));

            // Append timestamp to the original filename
            String newFileName = FilenameUtils.getBaseName(filename) + "_" + timestamp + fileExtension;

            // Save to S3 bucket
            return bucketService.save(file.getBytes(), folderName, newFileName, "ServiceProvider");

        } catch (IOException e) {
            logger.error("Failed to save PDF to S3", e);
            throw new RuntimeException("Failed to save PDF to S3: " + e.getMessage());
        }
    }
}
