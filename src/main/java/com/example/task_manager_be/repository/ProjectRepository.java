package com.example.task_manager_be.repository;

import com.example.task_manager_be.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByMembersId(Long userId);

}
