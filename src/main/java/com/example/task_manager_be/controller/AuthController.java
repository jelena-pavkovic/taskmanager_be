package com.example.task_manager_be.controller;

import com.example.task_manager_be.dto.AuthResponse;
import com.example.task_manager_be.dto.LoginRequest;
import com.example.task_manager_be.dto.RefreshRequest;
import com.example.task_manager_be.dto.RegisterRequest;
import com.example.task_manager_be.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest req) {
        authService.register(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {
        return authService.login(req);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshRequest req) {
        return authService.refresh(req);
    }

}
