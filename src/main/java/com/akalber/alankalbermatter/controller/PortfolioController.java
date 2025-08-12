package com.akalber.alankalbermatter.controller;

import com.akalber.alankalbermatter.model.*;
import com.akalber.alankalbermatter.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/portfolio")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
public class PortfolioController {

    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private SkillRepository skillRepository;

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getPortfolioSummary() {
        Map<String, Object> summary = new HashMap<>();

        // Información personal
        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        if (!personalInfoList.isEmpty()) {
            summary.put("personalInfo", personalInfoList.get(0));
        }

        // Proyectos destacados
        List<Project> featuredProjects = projectRepository.findByIsFeaturedTrueOrderByDisplayOrderAsc();
        summary.put("featuredProjects", featuredProjects);

        // Experiencia actual
        List<Experience> currentExperiences = experienceRepository.findByIsCurrentTrueOrderByStartDateDesc();
        summary.put("currentExperience", currentExperiences);

        // Habilidades destacadas
        List<Skill> featuredSkills = skillRepository.findByIsFeaturedTrueOrderByDisplayOrderAsc();
        summary.put("featuredSkills", featuredSkills);

        // Estadísticas
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalProjects", projectRepository.count());
        stats.put("totalExperiences", experienceRepository.count());
        stats.put("totalSkills", skillRepository.count());
        summary.put("stats", stats);

        return ResponseEntity.ok(summary);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        health.put("message", "Portfolio API is running");
        return ResponseEntity.ok(health);
    }
}
