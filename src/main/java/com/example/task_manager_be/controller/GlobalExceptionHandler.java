package com.example.task_manager_be.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String,String>> notFound(EntityNotFoundException e) {
        return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String,String>> forbidden(AccessDeniedException e) {
        return ResponseEntity.status(403).body(Map.of("error", e.getMessage()));
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,String>> badRequest(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
}
