package com.HomeSahulat.service.impl;

import com.HomeSahulat.Util.EmailUtils;
import com.HomeSahulat.Util.Helper;
import com.HomeSahulat.config.otp.InfoBip;
import com.HomeSahulat.dto.LoginCredentials;
import com.HomeSahulat.dto.UserDto;
import com.HomeSahulat.exception.InvalidResetCodeException;
import com.HomeSahulat.exception.RecordNotFoundException;
import com.HomeSahulat.model.Role;
import com.HomeSahulat.model.User;
import com.HomeSahulat.repository.LocationRepository;
import com.HomeSahulat.repository.RoleRepository;
import com.HomeSahulat.repository.UserRepository;
import com.HomeSahulat.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.HomeSahulat.Util.Helper.formatPhoneNumber;
import static com.HomeSahulat.Util.Helper.generateRandomOTP;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Helper helper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final InfoBip infoBip;
    private final EmailUtils emailUtils;
    private final RoleRepository roleRepository;
    private final LocationRepository locationRepository;

    public UserServiceImpl(UserRepository userRepository, Helper helper, BCryptPasswordEncoder bCryptPasswordEncoder, InfoBip infoBip, EmailUtils emailUtils, RoleRepository roleRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.helper = helper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.infoBip = infoBip;
        this.emailUtils = emailUtils;
        this.roleRepository = roleRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    @Transactional
    public UserDto registerUser(UserDto userdto) {
        String otp = generateRandomOTP();
        String otpMessage = "Your OTP is: " + otp;

        User user = toEntity(userdto);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setStatus(true);
        user.setOtpFlag(false);

        if(userdto.getLocation() != null){
            user.setLocation((locationRepository.findById(user.getLocation().getId())
                    .orElseThrow(() -> new RecordNotFoundException(String.format("Location not found for id => %d", user.getLocation().getId())))));
        }

        if(userdto.getRoles() != null){
            Set<Role> roleList = new HashSet<>();
            for(Role role: user.getRoles()){
                roleRepository.findById(role.getId())
                        .orElseThrow(()-> new RecordNotFoundException("Role not found"));
                roleList.add(role);
            }
            user.setRoles(roleList);
        }

        String phoneNumber = formatPhoneNumber(userdto.getPhone());
        boolean otpCheck = infoBip.sendSMS("HomeSahulat", phoneNumber, otpMessage);

        if (otpCheck) {
            user.setOtp(bCryptPasswordEncoder.encode(otp));
            userRepository.save(user);
            return toDto(user);
        } else {
            throw new RecordNotFoundException("Registration Failed, OTP not sent");
        }
    }

    @Override
    @Transactional
    public Boolean checkUserVerified(LoginCredentials loginCredentials) {
        // Step 1: Retrieve the user by phone number
        User user = userRepository.findByPhone(loginCredentials.getPhone());

        // Step 2: Check if the user exists and the password matches
        if (user != null && bCryptPasswordEncoder.matches(loginCredentials.getPassword(), user.getPassword())) {
            if (!user.getOtpFlag()) {
                throw new RecordNotFoundException("User is not verified");
            }
            return true;
        } else {
            // User not found or password doesn't match
            return false;
        }
    }


    @Transactional
    @Override
    public Boolean checkOtpVerification(Long id, String otp) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("User not found for id => %d", id)));

        boolean otpCheck = bCryptPasswordEncoder.matches(otp, user.getOtp());
        if(otpCheck){
            userRepository.setOtpFlagTrue(id);
        }
        return otpCheck;
    }

    @Override
    public UserDto resendOtp(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("User not found for id => %d", id)));

        String otp = generateRandomOTP();
        String otpMessage = "Your OTP is: " + otp;

        String phoneNumber = formatPhoneNumber(user.getPhone());
        boolean otpCheck = infoBip.sendSMS("HomeSahulat", phoneNumber, otpMessage);

        if (otpCheck) {
            user.setOtp(bCryptPasswordEncoder.encode(otp));
            userRepository.save(user);
            return toDto(user);
        } else {
            throw new RecordNotFoundException("OTP not sent");
        }
    }


    @Override
    public List<UserDto> getAll() {
        List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();

        for (User user : userList) {
            UserDto userDto = toDto(user);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    @Override
    public UserDto findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return toDto(user);
        } else {
            throw new RecordNotFoundException(String.format("User not found for id => %d", id));
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("User not found for id => %d", id)));
        userRepository.setStatusInactive(user.getId());
    }

    @Override
    @Transactional
    public UserDto update(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("User not found for id => %d", id)));

        existingUser.setStatus(userDto.getStatus());
        existingUser.setName(userDto.getName());
        existingUser.setPassword(userDto.getPassword());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setLocation(userDto.getLocation());
        existingUser.setPhone(userDto.getPhone());
        existingUser.setProfilePictureUrl(userDto.getProfilePictureUrl());
        existingUser.setDeviceId(userDto.getDeviceId());
        existingUser.setOtp(userDto.getOtp());
        existingUser.setOtpFlag(userDto.getOtpFlag());

        existingUser.setLocation(locationRepository.findById(userDto.getLocation().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Location not found for id => %d", userDto.getLocation().getId()))));

        existingUser.getRoles().removeIf(role -> !userDto.getRoles().contains(role));

        Set<Role> roleList = userDto.getRoles().stream()
                .map(role -> roleRepository.findById(role.getId())
                        .orElseThrow(() -> new RecordNotFoundException("Role not found")))
                .collect(Collectors.toSet());

        existingUser.setRoles(roleList);
        User updatedUser = userRepository.save(existingUser);
        return toDto(updatedUser);
    }

    @Override
    public UserDto getLoggedInUser() {
        return toDto(helper.getCurrentUser());
    }

    @Override
    @Transactional
    public void forgotPassword(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));

        String resetCode = helper.generateResetCode();

        // Save reset code and timestamp in the database
        user.setResetCode(resetCode);
        user.setResetCodeTimestamp(LocalDateTime.now());
        userRepository.save(user);

        // Send email with reset code
        emailUtils.sendPasswordResetEmail(user, resetCode);
    }

    @Override
    @Transactional
    public void resetPassword(String userEmail, String resetCode, String newPassword) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));

        // Check if reset code is valid and not expired
        if (helper.isValidResetCode(user, resetCode)) {
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new InvalidResetCodeException("Invalid or expired reset code");
        }
    }

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .createdAt(user.getCreatedAt())
                .name(user.getName())
                .password(user.getPassword())
                .email(user.getEmail())
                .phone(user.getPhone())
                .profilePictureUrl(user.getProfilePictureUrl())
                .deviceId(user.getDeviceId())
                .otp(user.getOtp())
                .otpFlag(user.getOtpFlag())
                .resetCode(user.getResetCode())
                .resetCodeTimestamp(user.getResetCodeTimestamp())
                .location(user.getLocation())
                .roles(user.getRoles())
                .status(user.getStatus())
                .build();
    }

    public User toEntity(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .createdAt(userDto.getCreatedAt())
                .name(userDto.getName())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .profilePictureUrl(userDto.getProfilePictureUrl())
                .deviceId(userDto.getDeviceId())
                .otp(userDto.getOtp())
                .otpFlag(userDto.getOtpFlag())
                .resetCode(userDto.getResetCode())
                .resetCodeTimestamp(userDto.getResetCodeTimestamp())
                .location(userDto.getLocation())
                .roles(userDto.getRoles())
                .status(userDto.getStatus())
                .build();
    }
}
