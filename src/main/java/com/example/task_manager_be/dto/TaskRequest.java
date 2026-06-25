package com.example.task_manager_be.dto;

import com.example.task_manager_be.model.TaskStatus;

import java.time.LocalDate;
import java.util.Set;

public record TaskRequest(String title, String description, TaskStatus status, LocalDate dueDate, Long projectId, Long assigneeId, Set<Long> categoryIds) {
}
