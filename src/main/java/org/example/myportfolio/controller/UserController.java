package org.example.myportfolio.controller;

import lombok.RequiredArgsConstructor;
import org.example.myportfolio.request.UserRegisterRequest;
import org.example.myportfolio.request.UserRequest;
import org.example.myportfolio.response.AuthResponse;
import org.example.myportfolio.response.UserResponse;
import org.example.myportfolio.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public String createUser(@RequestBody UserRegisterRequest userRequest) {
        return userService.registerUser(userRequest);
    }

    @PostMapping("/verify/{email}/{otp}")
    public AuthResponse verifyUser(@PathVariable String email, @PathVariable String otp, @RequestBody UserRequest userRegisterRequest) {
    return userService.verifyOtp(email, otp, userRegisterRequest);
    }
}
