package com.example.task_manager_be.dto;

import java.util.List;

public record AuthResponse(String accessToken, String refreshToken, String username, List<String> roles) {
}
