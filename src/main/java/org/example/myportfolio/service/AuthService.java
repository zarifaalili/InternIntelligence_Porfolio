package org.example.myportfolio.service;

import lombok.RequiredArgsConstructor;
import org.example.myportfolio.dao.entity.User;
import org.example.myportfolio.dao.repository.UserRepository;
import org.example.myportfolio.request.AuthRequest;
import org.example.myportfolio.request.RefreshTokenRequest;
import org.example.myportfolio.response.AuthResponse;
import org.example.myportfolio.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthResponse authenticate(AuthRequest req) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

        String username = authentication.getName();

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateAccessToken(username, user.getId());
        String refresh = jwtUtil.generateRefreshToken(username, user.getId());
        return new AuthResponse(token, refresh);
    }

    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        if (jwtUtil.isTokenExpired(refreshTokenRequest.getRefreshToken())) {
            throw new RuntimeException("Refresh token is expired");
        }
        var username = jwtUtil.extractUsername(refreshTokenRequest.getRefreshToken());
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var token = jwtUtil.generateAccessToken(username, user.getId());
        var refreshToken = jwtUtil.generateRefreshToken(username, user.getId());
        return new AuthResponse(token, refreshToken);
    }
}
