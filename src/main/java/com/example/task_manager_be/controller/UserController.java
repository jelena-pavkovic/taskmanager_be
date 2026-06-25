package com.example.task_manager_be.controller;

import com.example.task_manager_be.dto.RoleRequest;
import com.example.task_manager_be.dto.UserResponse;
import com.example.task_manager_be.dto.UserUpdateRequest;
import com.example.task_manager_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserResponse> all() {
        return userService.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id,
                                               @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userService.update(id, request));
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<Void> addRole(@PathVariable Long id, @RequestBody RoleRequest req) {
        userService.addRole(id, req.roleName());
        return ResponseEntity.ok().build();
    }
}
