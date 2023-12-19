package com.HomeSahulat.controller;

import com.HomeSahulat.config.security.JwtUtil;
import com.HomeSahulat.dto.AuthenticationResponse;
import com.HomeSahulat.dto.LoginCredentials;
import com.HomeSahulat.dto.UserDto;
import com.HomeSahulat.exception.RecordNotFoundException;
import com.HomeSahulat.service.UserService;
import com.HomeSahulat.service.impl.MyUserDetailServiceImplementation;
import org.springframework.http.HttpStatus;
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
                    new UsernamePasswordAuthenticationToken(loginCredentials.getPhone(), loginCredentials.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect PhoneNumber or Password! ", e);
        }

        if(userService.checkUserVerified(loginCredentials)){
            UserDetails userDetails = myUserDetailService.loadUserByUsername(loginCredentials.getPhone());
            String jwtToken = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
        }
        else {
            throw new RecordNotFoundException("User not verified");
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@Valid @RequestBody UserDto userdto) {
        UserDto user = userService.registerUser(userdto);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signup/resend-otp/user/{id}")
    public ResponseEntity<UserDto> resendOtp(@PathVariable Long id) {
        UserDto user = userService.resendOtp(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/signup/user/{id}/otp-verification/{otp}")
    public ResponseEntity<Boolean> otpVerification(@PathVariable Long id, @PathVariable String otp) {
        return ResponseEntity.ok(userService.checkOtpVerification(id,otp));
    }

    @PostMapping("user/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        userService.forgotPassword(email);
        return new ResponseEntity<>("Password reset email sent successfully.", HttpStatus.OK);
    }

    @PostMapping("user/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam String email,
            @RequestParam String resetCode,
            @RequestParam String newPassword) {
        userService.resetPassword(email, resetCode, newPassword);
        return new ResponseEntity<>("Password reset successfully.", HttpStatus.OK);
    }
}
