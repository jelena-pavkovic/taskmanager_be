package com.example.task_manager_be.service;

import com.example.task_manager_be.dto.UserResponse;
import com.example.task_manager_be.dto.UserUpdateRequest;
import com.example.task_manager_be.model.Role;
import com.example.task_manager_be.model.User;
import com.example.task_manager_be.repository.RoleRepository;
import com.example.task_manager_be.repository.TaskRepository;
import com.example.task_manager_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor

public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TaskRepository taskRepository;

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional
    public void delete(Long id) {
        taskRepository.unassignUser(id);
        userRepository.deleteById(id);
    }

    public UserResponse update(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Korisnik nije pronađen"));

        user.setUsername(request.username());
        user.setEmail(request.email());

        List<Role> roles = request.roles().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Rola nije pronađena: " + roleName)))
                .toList();

        user.setRoles(new HashSet<>(roles));

        return toResponse(userRepository.save(user));
    }

    public void addRole(Long userId, String roleName) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Korisnik nije pronađen"));
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Rola nije pronađena"));
        user.getRoles().add(role);
        userRepository.save(user);
    }

    private UserResponse toResponse(User user) {
        List<String> roles = user.getRoles().stream().map(Role::getName).toList();
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                roles
        );
    }
}
