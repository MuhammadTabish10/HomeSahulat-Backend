package com.HomeSahulat.service;

import com.HomeSahulat.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDto registerUser(UserDto userdto);
    List<UserDto> getAll();
    UserDto findById(Long id);

    void deleteById(Long id);
    UserDto update(Long id, UserDto userDto);

}
