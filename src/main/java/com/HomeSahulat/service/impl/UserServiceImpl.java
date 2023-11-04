package com.HomeSahulat.service.impl;

import com.HomeSahulat.config.otp.InfoBip;
import com.HomeSahulat.dto.UserDto;
import com.HomeSahulat.exception.RecordNotFoundException;
import com.HomeSahulat.model.Role;
import com.HomeSahulat.model.User;
import com.HomeSahulat.repository.LocationRepository;
import com.HomeSahulat.repository.RoleRepository;
import com.HomeSahulat.repository.UserRepository;
import com.HomeSahulat.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static com.HomeSahulat.Util.Helper.formatPhoneNumber;
import static com.HomeSahulat.Util.Helper.generateRandomOTP;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final HttpServletRequest request;
    private final InfoBip infoBip;
    private final RoleRepository roleRepository;
    private final LocationRepository locationRepository;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, HttpServletRequest request, InfoBip infoBip, RoleRepository roleRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.request = request;
        this.infoBip = infoBip;
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
            user.setOtpFlag(true);
            userRepository.save(user);
            return toDto(user);
        } else {
            throw new RecordNotFoundException("Registration Failed, OTP not sent");
        }
    }

    @Override
    public Boolean checkOtpVerification(Long id, String otp) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("User not found for id => %d", id)));
        return bCryptPasswordEncoder.matches(otp, user.getOtp());
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
        existingUser.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        existingUser.setEmail(userDto.getEmail());
        existingUser.setFirstName(userDto.getFirstName());
        existingUser.setLastName(userDto.getLastName());
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

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .createdAt(user.getCreatedAt())
                .name(user.getName())
                .password(user.getPassword())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .profilePictureUrl(user.getProfilePictureUrl())
                .deviceId(user.getDeviceId())
                .otp(user.getOtp())
                .otpFlag(user.getOtpFlag())
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
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .phone(userDto.getPhone())
                .profilePictureUrl(userDto.getProfilePictureUrl())
                .deviceId(userDto.getDeviceId())
                .otp(userDto.getOtp())
                .otpFlag(userDto.getOtpFlag())
                .location(userDto.getLocation())
                .roles(userDto.getRoles())
                .status(userDto.getStatus())
                .build();
    }
}
