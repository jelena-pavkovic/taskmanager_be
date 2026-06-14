package com.example.task_manager_be.repository;

import com.example.task_manager_be.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
