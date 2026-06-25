package com.example.task_manager_be.controller;

import com.example.task_manager_be.dto.TaskRequest;
import com.example.task_manager_be.dto.TaskResponse;
import com.example.task_manager_be.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor

public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public List<TaskResponse> all(@RequestParam(required = false) Long projectId) {
        return taskService.findAll(projectId);
    }

    @GetMapping("/{id}")
    public TaskResponse one(@PathVariable Long id) { return taskService.findById(id); }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@RequestBody @Valid TaskRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(req));
    }

    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable Long id,
                               @RequestBody @Valid TaskRequest req) {
        return taskService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
