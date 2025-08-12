package com.akalber.alankalbermatter.controller;

import com.akalber.alankalbermatter.model.Skill;
import com.akalber.alankalbermatter.repository.SkillRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    @GetMapping
    public List<Skill> getAllSkills() {
        return skillRepository.findAllByOrderByCategoryAscDisplayOrderAsc();
    }

    @GetMapping("/featured")
    public List<Skill> getFeaturedSkills() {
        return skillRepository.findByIsFeaturedTrueOrderByDisplayOrderAsc();
    }

    @GetMapping("/category/{category}")
    public List<Skill> getSkillsByCategory(@PathVariable String category) {
        return skillRepository.findByCategoryOrderByDisplayOrderAsc(category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Skill> getSkillById(@PathVariable Long id) {
        Optional<Skill> skill = skillRepository.findById(id);
        return skill.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Skill> searchSkills(@RequestParam String name) {
        return skillRepository.findByNameContainingIgnoreCase(name);
    }

    @GetMapping("/level/{level}")
    public List<Skill> getSkillsByLevel(@PathVariable Integer level) {
        return skillRepository.findByLevel(level);
    }

    @PostMapping
    public ResponseEntity<Skill> createSkill(@Valid @RequestBody Skill skill) {
        try {
            Skill savedSkill = skillRepository.save(skill);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSkill);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable Long id, @Valid @RequestBody Skill skillDetails) {
        Optional<Skill> optionalSkill = skillRepository.findById(id);

        if (optionalSkill.isPresent()) {
            Skill skill = optionalSkill.get();
            skill.setName(skillDetails.getName());
            skill.setCategory(skillDetails.getCategory());
            skill.setLevel(skillDetails.getLevel());
            skill.setIconUrl(skillDetails.getIconUrl());
            skill.setDisplayOrder(skillDetails.getDisplayOrder());
            skill.setIsFeatured(skillDetails.getIsFeatured());

            Skill updatedSkill = skillRepository.save(skill);
            return ResponseEntity.ok(updatedSkill);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        if (skillRepository.existsById(id)) {
            skillRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
