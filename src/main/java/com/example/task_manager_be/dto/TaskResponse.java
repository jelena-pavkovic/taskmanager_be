package com.example.task_manager_be.dto;

import com.example.task_manager_be.model.TaskStatus;
import java.util.Set;
import com.example.task_manager_be.dto.CategoryResponse;
import java.time.LocalDate;

public record TaskResponse(Long id, String title, String description, TaskStatus status, LocalDate dueDate, Long projectId, String assigneeUsername, Set<CategoryResponse> categories
) {
}
