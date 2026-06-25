package com.example.task_manager_be.repository;

import com.example.task_manager_be.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Modifying
    @Query(value = "DELETE FROM task_categories WHERE category_id = :categoryId", nativeQuery = true)
    void deleteTaskCategoriesByCategoryId(@Param("categoryId") Long categoryId);
}
