package com.akalber.alankalbermatter.controller;

import com.akalber.alankalbermatter.model.Project;
import com.akalber.alankalbermatter.repository.ProjectRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping
    public List<Project> getAllProjects() {
        return projectRepository.findAllByOrderByDisplayOrderAsc();
    }

    @GetMapping("/featured")
    public List<Project> getFeaturedProjects() {
        return projectRepository.findByIsFeaturedTrueOrderByDisplayOrderAsc();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Project> searchProjects(@RequestParam String query) {
        return projectRepository.findByTitleContainingIgnoreCase(query);
    }

    @GetMapping("/technology/{technology}")
    public List<Project> getProjectsByTechnology(@PathVariable String technology) {
        return projectRepository.findByTechnology(technology);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) {
        try {
            Project savedProject = projectRepository.save(project);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @Valid @RequestBody Project projectDetails) {
        Optional<Project> optionalProject = projectRepository.findById(id);

        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.setTitle(projectDetails.getTitle());
            project.setDescription(projectDetails.getDescription());
            project.setShortDescription(projectDetails.getShortDescription());
            project.setImageUrl(projectDetails.getImageUrl());
            project.setGithubUrl(projectDetails.getGithubUrl());
            project.setLiveUrl(projectDetails.getLiveUrl());
            project.setTechnologies(projectDetails.getTechnologies());
            project.setStartDate(projectDetails.getStartDate());
            project.setEndDate(projectDetails.getEndDate());
            project.setIsFeatured(projectDetails.getIsFeatured());
            project.setDisplayOrder(projectDetails.getDisplayOrder());

            Project updatedProject = projectRepository.save(project);
            return ResponseEntity.ok(updatedProject);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
