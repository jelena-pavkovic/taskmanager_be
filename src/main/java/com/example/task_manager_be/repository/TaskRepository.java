package com.example.task_manager_be.repository;

import com.example.task_manager_be.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository  extends JpaRepository<Task, Long> {
    List<Task> findByProjectId(Long projectId);

    List<Task> findByAssigneeId(Long userId);

    @Modifying
    @Query("UPDATE Task t SET t.assignee = null WHERE t.assignee.id = :userId")
    void unassignUser(@Param("userId") Long userId);
}
