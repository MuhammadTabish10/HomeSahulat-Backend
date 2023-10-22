package com.HomeSahulat.service.impl;

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

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;
    private final LocationRepository locationRepository;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    @Transactional
    public UserDto registerUser(UserDto userdto) {
        User user = toEntity(userdto);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setStatus(true);

        user.setLocation((locationRepository.findById(user.getLocation().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Location not found for id => %d", user.getLocation().getId())))));

        Set<Role> roleList = new HashSet<>();
        for(Role role: user.getRoles()){
            roleRepository.findById(role.getId())
                    .orElseThrow(()-> new RecordNotFoundException("Role not found"));
            roleList.add(role);
        }

        user.setRoles(roleList);
        userRepository.save(user);
        return toDto(user);
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
                .location(userDto.getLocation())
                .roles(userDto.getRoles())
                .status(userDto.getStatus())
                .build();
    }
}
