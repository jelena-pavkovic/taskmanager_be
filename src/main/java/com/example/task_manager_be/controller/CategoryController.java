package com.example.task_manager_be.controller;


import com.example.task_manager_be.dto.CategoryRequest;
import com.example.task_manager_be.dto.CategoryResponse;
import com.example.task_manager_be.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> all() {
        return categoryService.findAll();
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody CategoryRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(req));
    }

    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable Long id, @RequestBody CategoryRequest req) {
        return categoryService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
