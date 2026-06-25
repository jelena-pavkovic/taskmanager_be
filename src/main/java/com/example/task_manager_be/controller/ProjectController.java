package com.example.task_manager_be.controller;


import com.example.task_manager_be.dto.ProjectRequest;
import com.example.task_manager_be.dto.ProjectResponse;
import com.example.task_manager_be.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor

public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    public List<ProjectResponse> all() {
        return projectService.findAll();
    }

    @GetMapping("/{id}")
    public ProjectResponse one(@PathVariable Long id) {
        return projectService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> create(@RequestBody ProjectRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.create(req));
    }

    @PutMapping("/{id}")
    public ProjectResponse update(@PathVariable Long id, @RequestBody ProjectRequest req) {
        return projectService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/members/{userId}")
    public ResponseEntity<Void> addMember(@PathVariable Long id, @PathVariable Long userId) {
        projectService.addMember(id, userId);
        return ResponseEntity.ok().build();
    }

}
