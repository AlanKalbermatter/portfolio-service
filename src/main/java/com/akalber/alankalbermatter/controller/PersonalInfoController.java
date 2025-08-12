package com.akalber.alankalbermatter.controller;

import com.akalber.alankalbermatter.model.PersonalInfo;
import com.akalber.alankalbermatter.repository.PersonalInfoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personal-info")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
public class PersonalInfoController {

    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    @GetMapping
    public ResponseEntity<PersonalInfo> getPersonalInfo() {
        List<PersonalInfo> infoList = personalInfoRepository.findAll();
        if (!infoList.isEmpty()) {
            return ResponseEntity.ok(infoList.get(0)); // Retorna el primer (y único) registro
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PersonalInfo> createPersonalInfo(@Valid @RequestBody PersonalInfo personalInfo) {
        try {
            // Verificar si ya existe información personal
            List<PersonalInfo> existingInfo = personalInfoRepository.findAll();
            if (!existingInfo.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .build(); // Ya existe información personal
            }

            PersonalInfo savedInfo = personalInfoRepository.save(personalInfo);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping
    public ResponseEntity<PersonalInfo> updatePersonalInfo(@Valid @RequestBody PersonalInfo personalInfoDetails) {
        List<PersonalInfo> infoList = personalInfoRepository.findAll();

        if (!infoList.isEmpty()) {
            PersonalInfo existingInfo = infoList.get(0);
            existingInfo.setName(personalInfoDetails.getName());
            existingInfo.setJobTitle(personalInfoDetails.getJobTitle());
            existingInfo.setBio(personalInfoDetails.getBio());
            existingInfo.setEmail(personalInfoDetails.getEmail());
            existingInfo.setPhone(personalInfoDetails.getPhone());
            existingInfo.setLocation(personalInfoDetails.getLocation());
            existingInfo.setProfileImageUrl(personalInfoDetails.getProfileImageUrl());
            existingInfo.setResumeUrl(personalInfoDetails.getResumeUrl());
            existingInfo.setLinkedinUrl(personalInfoDetails.getLinkedinUrl());
            existingInfo.setGithubUrl(personalInfoDetails.getGithubUrl());
            existingInfo.setTwitterUrl(personalInfoDetails.getTwitterUrl());
            existingInfo.setWebsiteUrl(personalInfoDetails.getWebsiteUrl());

            PersonalInfo updatedInfo = personalInfoRepository.save(existingInfo);
            return ResponseEntity.ok(updatedInfo);
        } else {
            // Si no existe, crear nuevo
            PersonalInfo savedInfo = personalInfoRepository.save(personalInfoDetails);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedInfo);
        }
    }
}
