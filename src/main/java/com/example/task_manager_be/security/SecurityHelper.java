package com.example.task_manager_be.security;

import com.example.task_manager_be.model.User;
import com.example.task_manager_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class SecurityHelper {
    private final UserRepository userRepository;

    public User currentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow();
    }

    public boolean isAdmin(User u) {
        return u.getRoles().stream().anyMatch(r -> r.getName().equals("ADMIN"));
    }
}
