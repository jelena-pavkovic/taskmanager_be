package com.example.task_manager_be.service;


import com.example.task_manager_be.dto.CategoryRequest;
import com.example.task_manager_be.dto.CategoryResponse;
import com.example.task_manager_be.model.Category;
import com.example.task_manager_be.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream().map(this::toResponse).toList();
    }

    public CategoryResponse create(CategoryRequest req) {
        Category category = new Category();
        category.setName(req.name());
        category.setColor(req.color());

        return toResponse(categoryRepository.save(category));
    }

    public CategoryResponse update(Long id, CategoryRequest req) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Kategorija nije pronađena"));
        category.setName(req.name());
        category.setColor(req.color());

        return toResponse(categoryRepository.save(category));
    }

    @Transactional
    public void delete(Long id) {
        categoryRepository.deleteTaskCategoriesByCategoryId(id);
        categoryRepository.deleteById(id);
    }

    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getColor()
        );
    }
}
