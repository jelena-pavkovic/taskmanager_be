package com.example.task_manager_be.service;


import com.example.task_manager_be.dto.ProjectRequest;
import com.example.task_manager_be.dto.ProjectResponse;
import com.example.task_manager_be.model.Project;
import com.example.task_manager_be.model.User;
import com.example.task_manager_be.repository.ProjectRepository;
import com.example.task_manager_be.repository.UserRepository;
import com.example.task_manager_be.security.SecurityHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final SecurityHelper securityHelper;

    public ProjectResponse findById(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Projekat nije pronađen"));
        return toResponse(project);
    }

    public ProjectResponse create(ProjectRequest req) {
        User me = securityHelper.currentUser();

        Project project = new Project();
        project.setName(req.name());
        project.setDescription(req.description());
        project.setCreatedBy(me);

        return toResponse(projectRepository.save(project));
    }

    public ProjectResponse update(Long id, ProjectRequest req) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Projekat nije pronađen"));
        project.setName(req.name());
        project.setDescription(req.description());

        return toResponse(projectRepository.save(project));
    }

    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    public void addMember(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Projekat nije pronađen"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Korisnik nije pronađen"));
        project.getMembers().add(user);
        projectRepository.save(project);
    }

    private ProjectResponse toResponse(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription()
        );
    }

    public List<ProjectResponse> findAll() {
        User me = securityHelper.currentUser();
        List<Project> projects = securityHelper.isAdmin(me)
                ? projectRepository.findAll()
                : projectRepository.findByMembersId(me.getId());

        return projects.stream().map(this::toResponse).toList();
    }
}
