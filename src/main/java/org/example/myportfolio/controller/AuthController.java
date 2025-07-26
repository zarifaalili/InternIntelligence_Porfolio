package org.example.myportfolio.controller;

import lombok.RequiredArgsConstructor;
import org.example.myportfolio.request.AuthRequest;
import org.example.myportfolio.request.RefreshTokenRequest;
import org.example.myportfolio.response.AuthResponse;
import org.example.myportfolio.service.AuthService;
import org.example.myportfolio.service.PasswordResetService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final PasswordResetService resetService;

    @PostMapping("/token")
    public AuthResponse token(@RequestBody AuthRequest request) {
        return authService.authenticate(request);
    }

    @PostMapping("/token/refresh")
    public AuthResponse refresh(@RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }

    @PostMapping("/forgot-password")
    public void forgot(@RequestParam String email) {
        resetService.sendCode(email);
    }

    @PostMapping("/verify-code")
    public void verify(@RequestParam String email, @RequestParam String code) {
        resetService.verifyCode(email, code);
    }

    @PostMapping("/reset-password")
    public void reset(@RequestParam String email,
                      @RequestParam String code,
                      @RequestParam String newPassword,
                      @RequestParam String confirmPassword) {
        resetService.resetPassword(email, code, newPassword, confirmPassword);
    }
}

