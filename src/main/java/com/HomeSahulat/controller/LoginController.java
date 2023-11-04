package com.HomeSahulat.controller;

import com.HomeSahulat.config.security.JwtUtil;
import com.HomeSahulat.dto.AuthenticationResponse;
import com.HomeSahulat.dto.LoginCredentials;
import com.HomeSahulat.dto.UserDto;
import com.HomeSahulat.service.UserService;
import com.HomeSahulat.service.impl.MyUserDetailServiceImplementation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final MyUserDetailServiceImplementation myUserDetailService;
    private final UserService userService;

    public LoginController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, MyUserDetailServiceImplementation myUserDetailService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.myUserDetailService = myUserDetailService;
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody LoginCredentials loginCredentials) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginCredentials.getName(), loginCredentials.getPassword())
            );
        } catch (Exception e) {
            throw new BadCredentialsException("Incorrect Username or Password! ", e);
        }

        UserDetails userDetails = myUserDetailService.loadUserByUsername(loginCredentials.getName());
        String jwtToken = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserDto userdto) {
        userService.registerUser(userdto);
        return ResponseEntity.ok("User registered successfully.");
    }

    @GetMapping("/signup/user/{id}/otp-verification/{otp}")
    public ResponseEntity<Boolean> otpVerification(@PathVariable Long id, @PathVariable String otp) {
        return ResponseEntity.ok(userService.checkOtpVerification(id,otp));
    }
}
