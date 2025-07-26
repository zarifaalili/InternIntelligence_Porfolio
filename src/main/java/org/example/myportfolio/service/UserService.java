package org.example.myportfolio.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.myportfolio.dao.entity.User;
import org.example.myportfolio.dao.repository.UserRepository;
import org.example.myportfolio.mapper.UserMapper;

import org.example.myportfolio.model.Role;
import org.example.myportfolio.request.UserRegisterRequest;
import org.example.myportfolio.request.UserRequest;
import org.example.myportfolio.response.AuthResponse;
import org.example.myportfolio.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@AllArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;


    private final ConcurrentMap<String, String> otpStore = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Long> otpExpiry = new ConcurrentHashMap<>();


    public String registerUser(UserRegisterRequest userRegisterRequest) {
        log.info("Actionlog.registerUser.start : ");
        var existingUser = userRepository.findByEmail(userRegisterRequest.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists with this email " + userRegisterRequest.getEmail());
        }

        if (!userRegisterRequest.getPassword().equals(userRegisterRequest.getConfirmedPassword())) {
            throw new RuntimeException("Password and confirmed password do not match");
        }

        String otp = generateOtp();
        Long expiry = System.currentTimeMillis() + 5 * 60 * 1000;

        otpStore.put(userRegisterRequest.getEmail(), otp);
        otpExpiry.put(userRegisterRequest.getEmail(), expiry);

        emailService.sendOtp(userRegisterRequest.getEmail(), otp);
        log.info("Actionlog.registerUser.end : ");
        return "otp sent to " + userRegisterRequest.getEmail();

    }

    public AuthResponse verifyOtp(String email, String otp, UserRequest userRegisterRequest) { //
        log.info("Actionlog.verifyOtp.start : ");
        String storedOtp = otpStore.get(email);
        Long expiry = otpExpiry.get(email);


        if (storedOtp == null || !storedOtp.equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }

        if (System.currentTimeMillis() > expiry) {
            throw new RuntimeException("OTP has expired");
        }

        otpStore.remove(email);
        otpExpiry.remove(email);

        var user = userMapper.toEntity(userRegisterRequest);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        user.setRoles(Set.of("USER"));

        var savedUser = userRepository.save(user);

        String accessToken = jwtUtil.generateAccessToken(email, savedUser.getId());
        String refreshToken = jwtUtil.generateRefreshToken(email, savedUser.getId());


        log.info("Actionlog.verifyOtp.end : ");
        return new AuthResponse(accessToken, refreshToken);


    }


    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
