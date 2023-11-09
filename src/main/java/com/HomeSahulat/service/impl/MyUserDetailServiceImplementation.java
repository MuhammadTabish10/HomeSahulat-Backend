package com.HomeSahulat.service.impl;

import com.HomeSahulat.dto.CustomUserDetail;
import com.HomeSahulat.model.User;
import com.HomeSahulat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailServiceImplementation implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        User user = userRepository.findByPhone(phone);

        if(user == null) {
            throw new UsernameNotFoundException("User not found with phone number: " + phone);
        }
        return new CustomUserDetail(user);
    }
}
