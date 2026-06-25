package com.example.task_manager_be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tasks", indexes = {
        @Index(name = "idx_task_project", columnList = "project_id"),
        @Index(name = "idx_task_assignee", columnList = "assignee_id"),
        @Index(name = "idx_task_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)

    private TaskStatus status = TaskStatus.TODO;
    private LocalDate dueDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id")
    @JsonIgnore

    private Project project;

    @ManyToOne
    @JoinColumn(name = "assignee_id")

    private User assignee;

    @ManyToMany
    @JoinTable(name = "task_categories", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

}
