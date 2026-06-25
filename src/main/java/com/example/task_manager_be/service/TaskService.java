package com.example.task_manager_be.service;

import com.example.task_manager_be.dto.CategoryResponse;
import com.example.task_manager_be.dto.TaskRequest;
import com.example.task_manager_be.dto.TaskResponse;
import com.example.task_manager_be.model.Category;
import com.example.task_manager_be.model.Project;
import com.example.task_manager_be.model.Task;
import com.example.task_manager_be.model.User;
import com.example.task_manager_be.repository.CategoryRepository;
import com.example.task_manager_be.repository.ProjectRepository;
import com.example.task_manager_be.repository.TaskRepository;
import com.example.task_manager_be.repository.UserRepository;
import com.example.task_manager_be.security.SecurityHelper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class TaskService {
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final SecurityHelper securityHelper;

    public List<TaskResponse> findAll(Long projectId) {
        List<Task> tasks = (projectId != null)
                ? taskRepository.findByProjectId(projectId)
                : taskRepository.findAll();
        return tasks.stream().map(this::toResponse).toList();
    }

    public TaskResponse findById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task nije pronađen"));
        return toResponse(task);
    }

    @Transactional
    public TaskResponse create(TaskRequest req) {
        Task task = new Task();
        task.setTitle(req.title());
        task.setDescription(req.description());
        task.setStatus(req.status());
        task.setDueDate(req.dueDate());

        Project project = projectRepository.findById(req.projectId()).orElseThrow(() -> new EntityNotFoundException("Projekat ne postoji"));
        task.setProject(project);

        if (req.assigneeId() != null) {
            User assignee = userRepository.findById(req.assigneeId()).orElseThrow(() -> new EntityNotFoundException("Korisnik ne postoji"));
            task.setAssignee(assignee);
        }

        if (req.categoryIds() != null && !req.categoryIds().isEmpty()) {
            Set<Category> categories = new HashSet<>(categoryRepository.findAllById(req.categoryIds()));
            task.setCategories(categories);
        }

        return toResponse(taskRepository.save(task));
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    private TaskResponse toResponse(Task task) {
        Set<CategoryResponse> cats = task.getCategories() != null
                ? task.getCategories().stream()
                .map(c -> new CategoryResponse(c.getId(), c.getName(), c.getColor()))
                .collect(Collectors.toSet())
                : Set.of();

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getDueDate(),
                task.getProject() != null ? task.getProject().getId() : null,  // zaštita od null
                task.getAssignee() != null ? task.getAssignee().getUsername() : null,
                cats
        );
    }

    @Transactional
    public TaskResponse update(Long taskId, TaskRequest req) {
        User me = securityHelper.currentUser();
        Task t = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Zadatak ne postoji"));

        boolean assignedToMe = t.getAssignee() != null && t.getAssignee().getId().equals(me.getId());

        if (!securityHelper.isAdmin(me) && !assignedToMe)
            throw new AccessDeniedException("Zadatak ti nije dodeljen");

        if (securityHelper.isAdmin(me)) {
            t.setTitle(req.title());
            t.setDescription(req.description());
            t.setStatus(req.status());
            t.setDueDate(req.dueDate());

            if (req.assigneeId() != null) {
                User assignee = userRepository.findById(req.assigneeId()).orElseThrow();
                t.setAssignee(assignee);
            }
        }
        else {
            t.setStatus(req.status());
        }

        if (req.categoryIds() != null) {
            if (req.categoryIds().isEmpty()) {
                t.getCategories().clear();
            } else {
                Set<Category> categories = new HashSet<>(categoryRepository.findAllById(req.categoryIds()));
                t.setCategories(categories);
            }
        }

        taskRepository.save(t);
        return toResponse(t);
    }
}
