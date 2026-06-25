package com.example.task_manager_be.dto;

import java.util.List;

public record UserResponse(Long id, String username, String email, List<String> roles) {
}
