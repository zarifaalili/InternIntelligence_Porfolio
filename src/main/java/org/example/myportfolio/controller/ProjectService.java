package org.example.myportfolio.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.myportfolio.dao.repository.ProjectRepository;
import org.example.myportfolio.dao.repository.UserRepository;
import org.example.myportfolio.mapper.ProjectMapper;
import org.example.myportfolio.request.ProjectRequest;
import org.example.myportfolio.response.ProjectResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserRepository userRepository;

    public ProjectResponse createProject(ProjectRequest projectRequest) {
        log.info("Actionlog.createProject.start : ");
        var userId = getCurrentUserId();
        var user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        var existingProject = projectRepository.findByTitle(projectRequest.getTitle());
        if (existingProject) {
            throw new RuntimeException("Project already exists");
        }
        var entity = projectMapper.toEntity(projectRequest);
        entity.setUser(user);
        var savedEntity = projectRepository.save(entity);
        log.info("Actionlog.createProject.end : ");
        var response = projectMapper.toResponse(savedEntity);
        return response;
    }


    public ProjectResponse getProject(Long id) {
        log.info("Actionlog.getProject.start : projectId={}", id);
        var userId = getCurrentUserId();
        var project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));

        if (!project.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not authorized to view this project");
        }
        var response = projectMapper.toResponse(project);
        log.info("Actionlog.getProject.end : projectId={}", id);
        return response;
    }

    public void deleteProject(Long id) {
        log.info("Actionlog.deleteProject.start : projectId={}", id);
        var userId = getCurrentUserId();
        var project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
        if (!project.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not authorized to delete this project");
        }
        projectRepository.delete(project);
        log.info("Actionlog.deleteProject.end : projectId={}", id);
    }

    public List<ProjectResponse> getAllProjects() {
        log.info("Actionlog.getAllProjects.start : ");
        var userId = getCurrentUserId();
        var projects = projectRepository.findAllByUserId(userId);
        if (projects.isEmpty()) {
            throw new RuntimeException("No projects found");
        }
        var list = projects.stream().map(projectMapper::toResponse).toList();
        log.info("Actionlog.getAllProjects.end : ");
        return list;
    }

    public ProjectResponse updateProject(Long id, ProjectRequest projectRequest) {
        log.info("Actionlog.updateProject.start : projectId={}", id);
        var userId = getCurrentUserId();
        var project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
        if (!project.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not authorized to update this project");
        }
        project.setTitle(projectRequest.getTitle());
        project.setDescription(projectRequest.getDescription());
        project.setLink(projectRequest.getLink());
        var savedEntity = projectRepository.save(project);
        log.info("Actionlog.updateProject.end : projectId={}", id);
        var response = projectMapper.toResponse(savedEntity);
        return response;
    }

    public ProjectResponse patchUpdateProject(Long id, ProjectRequest projectRequest) {
        log.info("Actionlog.patchUpdateProject.start : projectId={}", id);
        var userId = getCurrentUserId();
        var project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
        if (!project.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not authorized to update this project");
        }
        if (projectRequest.getTitle() != null) {
            project.setTitle(projectRequest.getTitle());
        }
        if (projectRequest.getDescription() != null) {
            project.setDescription(projectRequest.getDescription());
        }
        if (projectRequest.getLink() != null) {
            project.setLink(projectRequest.getLink());
        }
        var savedEntity = projectRepository.save(project);
        log.info("Actionlog.patchUpdateProject.end : projectId={}", id);
        var response = projectMapper.toResponse(savedEntity);
        return response;
    }

    private Long getCurrentUserId() {
        return (Long) ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getAttribute("userId");
    }

}
