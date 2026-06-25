package com.example.task_manager_be.service;

import com.example.task_manager_be.dto.AuthResponse;
import com.example.task_manager_be.dto.LoginRequest;
import com.example.task_manager_be.dto.RefreshRequest;
import com.example.task_manager_be.dto.RegisterRequest;
import com.example.task_manager_be.model.Role;
import com.example.task_manager_be.model.User;
import com.example.task_manager_be.repository.RoleRepository;
import com.example.task_manager_be.repository.UserRepository;
import com.example.task_manager_be.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public void register(RegisterRequest req) {
        if (userRepository.existsByUsername(req.username()))
            throw new IllegalArgumentException("Username zauzet");
        if (userRepository.existsByEmail(req.email()))
            throw new IllegalArgumentException("Email zauzet");

        User user = new User();
        user.setUsername(req.username());
        user.setEmail(req.email());
        user.setPassword(passwordEncoder.encode(req.password()));

        Role userRole = roleRepository.findByName("USER").orElseThrow();
        user.getRoles().add(userRole);
        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest req) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.username(), req.password()));
        User user = userRepository.findByUsername(req.username()).orElseThrow();
        List<String> roles = user.getRoles().stream().map(Role::getName).toList();

        return new AuthResponse(jwtUtil.generateAccessToken(user.getUsername(), roles), jwtUtil.generateRefreshToken(user.getUsername()), user.getUsername(), roles);
    }

    public AuthResponse refresh(RefreshRequest req) {
        if (!jwtUtil.isValid(req.refreshToken()))
            throw new BadCredentialsException("Refresh token nevalidan");

        String username = jwtUtil.extractUsername(req.refreshToken());
        User user = userRepository.findByUsername(username).orElseThrow();
        List<String> roles = user.getRoles().stream().map(Role::getName).toList();

        return new AuthResponse(jwtUtil.generateAccessToken(username, roles), req.refreshToken(), username, roles);
    }

}
