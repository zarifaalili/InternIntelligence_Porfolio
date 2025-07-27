package org.example.myportfolio.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.myportfolio.request.ProjectRequest;
import org.example.myportfolio.response.ProjectResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;


    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ProjectResponse createProject(@RequestBody @Valid ProjectRequest projectRequest) {
        return projectService.createProject(projectRequest);
    }

    @PostMapping("/get/{id}")
    @PreAuthorize("hasRole('USER')")
    public ProjectResponse getProject(@PathVariable Long id) {
        return projectService.getProject(id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('USER')")
    public List<ProjectResponse> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PutMapping("/putUpdate/{id}")
    @PreAuthorize("hasRole('USER')")
    public ProjectResponse updateProject(@PathVariable Long id, @RequestBody @Valid ProjectRequest projectRequest) {
        return projectService.updateProject(id, projectRequest);
    }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasRole('USER')")
    public ProjectResponse patchProject(@PathVariable Long id, @RequestBody  ProjectRequest projectRequest) {
        return projectService.patchUpdateProject(id, projectRequest);
    }

}
