package com.akalber.alankalbermatter.controller;

import com.akalber.alankalbermatter.model.Experience;
import com.akalber.alankalbermatter.repository.ExperienceRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/experiences")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
public class ExperienceController {

    @Autowired
    private ExperienceRepository experienceRepository;

    @GetMapping
    public List<Experience> getAllExperiences() {
        return experienceRepository.findAllByOrderByStartDateDesc();
    }

    @GetMapping("/current")
    public List<Experience> getCurrentExperiences() {
        return experienceRepository.findByIsCurrentTrueOrderByStartDateDesc();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Experience> getExperienceById(@PathVariable Long id) {
        Optional<Experience> experience = experienceRepository.findById(id);
        return experience.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/company")
    public List<Experience> searchByCompany(@RequestParam String company) {
        return experienceRepository.findByCompanyContainingIgnoreCase(company);
    }

    @GetMapping("/search/position")
    public List<Experience> searchByPosition(@RequestParam String position) {
        return experienceRepository.findByPositionContainingIgnoreCase(position);
    }

    @PostMapping
    public ResponseEntity<Experience> createExperience(@Valid @RequestBody Experience experience) {
        try {
            Experience savedExperience = experienceRepository.save(experience);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedExperience);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Experience> updateExperience(@PathVariable Long id, @Valid @RequestBody Experience experienceDetails) {
        Optional<Experience> optionalExperience = experienceRepository.findById(id);

        if (optionalExperience.isPresent()) {
            Experience experience = optionalExperience.get();
            experience.setPosition(experienceDetails.getPosition());
            experience.setCompany(experienceDetails.getCompany());
            experience.setCompanyUrl(experienceDetails.getCompanyUrl());
            experience.setDescription(experienceDetails.getDescription());
            experience.setResponsibilities(experienceDetails.getResponsibilities());
            experience.setTechnologies(experienceDetails.getTechnologies());
            experience.setStartDate(experienceDetails.getStartDate());
            experience.setEndDate(experienceDetails.getEndDate());
            experience.setIsCurrent(experienceDetails.getIsCurrent());
            experience.setDisplayOrder(experienceDetails.getDisplayOrder());

            Experience updatedExperience = experienceRepository.save(experience);
            return ResponseEntity.ok(updatedExperience);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExperience(@PathVariable Long id) {
        if (experienceRepository.existsById(id)) {
            experienceRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
