package com.akalber.alankalbermatter.service;

import com.akalber.alankalbermatter.model.*;
import com.akalber.alankalbermatter.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class DataInitializationService implements CommandLineRunner {

    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Override
    public void run(String... args) throws Exception {
        initializePersonalInfo();
        initializeSkills();
        initializeExperiences();
        initializeProjects();
    }

    private void initializePersonalInfo() {
        if (personalInfoRepository.count() == 0) {
            PersonalInfo personalInfo = new PersonalInfo();
            personalInfo.setName("Alan Kalbermatter");
            personalInfo.setJobTitle("Full Stack Developer");
            personalInfo.setBio("Passionate software developer with expertise in modern web technologies. " +
                    "I love creating efficient, scalable solutions and learning new technologies.");
            personalInfo.setEmail("alan@example.com");
            personalInfo.setLocation("Argentina");
            personalInfo.setGithubUrl("https://github.com/alankalbermatter");
            personalInfo.setLinkedinUrl("https://linkedin.com/in/alankalbermatter");

            personalInfoRepository.save(personalInfo);
        }
    }

    private void initializeSkills() {
        if (skillRepository.count() == 0) {
            List<Skill> skills = Arrays.asList(
                // Frontend Skills
                new Skill("React", "Frontend", 4),
                new Skill("Angular", "Frontend", 4),
                new Skill("Vue.js", "Frontend", 3),
                new Skill("JavaScript", "Frontend", 5),
                new Skill("TypeScript", "Frontend", 4),
                new Skill("HTML5", "Frontend", 5),
                new Skill("CSS3", "Frontend", 4),

                // Backend Skills
                new Skill("Java", "Backend", 5),
                new Skill("Spring Boot", "Backend", 5),
                new Skill("Node.js", "Backend", 4),
                new Skill("Python", "Backend", 3),
                new Skill("REST APIs", "Backend", 5),

                // Database Skills
                new Skill("MySQL", "Database", 4),
                new Skill("PostgreSQL", "Database", 4),
                new Skill("MongoDB", "Database", 3),

                // DevOps Skills
                new Skill("Docker", "DevOps", 3),
                new Skill("Git", "DevOps", 5),
                new Skill("AWS", "DevOps", 3)
            );

            // Marcar algunos como destacados
            skills.get(0).setIsFeatured(true); // React
            skills.get(7).setIsFeatured(true); // Java
            skills.get(8).setIsFeatured(true); // Spring Boot
            skills.get(4).setIsFeatured(true); // TypeScript

            skillRepository.saveAll(skills);
        }
    }

    private void initializeExperiences() {
        if (experienceRepository.count() == 0) {
            Experience exp1 = new Experience();
            exp1.setPosition("Full Stack Developer");
            exp1.setCompany("Tech Solutions Inc.");
            exp1.setStartDate(LocalDate.of(2022, 1, 1));
            exp1.setIsCurrent(true);
            exp1.setDescription("Desarrollo de aplicaciones web modernas utilizando React y Spring Boot");
            exp1.setResponsibilities(Arrays.asList(
                "Desarrollo de aplicaciones web con React y TypeScript",
                "Creación de APIs REST con Spring Boot",
                "Implementación de bases de datos relacionales",
                "Colaboración en equipos ágiles"
            ));
            exp1.setTechnologies(Arrays.asList("React", "Spring Boot", "MySQL", "Docker"));

            Experience exp2 = new Experience();
            exp2.setPosition("Junior Developer");
            exp2.setCompany("StartupXYZ");
            exp2.setStartDate(LocalDate.of(2020, 6, 1));
            exp2.setEndDate(LocalDate.of(2021, 12, 31));
            exp2.setIsCurrent(false);
            exp2.setDescription("Desarrollo de funcionalidades frontend y backend");
            exp2.setResponsibilities(Arrays.asList(
                "Desarrollo de componentes React",
                "Mantenimiento de código legacy",
                "Testing y debugging"
            ));
            exp2.setTechnologies(Arrays.asList("JavaScript", "Node.js", "MongoDB"));

            experienceRepository.saveAll(Arrays.asList(exp1, exp2));
        }
    }

    private void initializeProjects() {
        if (projectRepository.count() == 0) {
            Project project1 = new Project();
            project1.setTitle("E-commerce Platform");
            project1.setDescription("Una plataforma de e-commerce completa desarrollada con React y Spring Boot. " +
                    "Incluye gestión de productos, carrito de compras, sistema de pagos y panel de administración.");
            project1.setShortDescription("Plataforma de e-commerce con React y Spring Boot");
            project1.setTechnologies(Arrays.asList("React", "Spring Boot", "MySQL", "Stripe API"));
            project1.setStartDate(LocalDate.of(2023, 3, 1));
            project1.setEndDate(LocalDate.of(2023, 8, 1));
            project1.setIsFeatured(true);
            project1.setGithubUrl("https://github.com/alankalbermatter/ecommerce-platform");
            project1.setDisplayOrder(1);

            Project project2 = new Project();
            project2.setTitle("Task Management App");
            project2.setDescription("Aplicación de gestión de tareas con funcionalidades de colaboración en tiempo real. " +
                    "Permite crear proyectos, asignar tareas y hacer seguimiento del progreso.");
            project2.setShortDescription("App de gestión de tareas colaborativa");
            project2.setTechnologies(Arrays.asList("Angular", "Node.js", "Socket.io", "MongoDB"));
            project2.setStartDate(LocalDate.of(2023, 9, 1));
            project2.setIsFeatured(true);
            project2.setGithubUrl("https://github.com/alankalbermatter/task-manager");
            project2.setDisplayOrder(2);

            Project project3 = new Project();
            project3.setTitle("Weather Dashboard");
            project3.setDescription("Dashboard del clima con pronósticos extendidos y visualizaciones interactivas.");
            project3.setShortDescription("Dashboard del clima con visualizaciones");
            project3.setTechnologies(Arrays.asList("Vue.js", "Chart.js", "Weather API"));
            project3.setStartDate(LocalDate.of(2022, 11, 1));
            project3.setEndDate(LocalDate.of(2023, 1, 1));
            project3.setIsFeatured(false);
            project3.setGithubUrl("https://github.com/alankalbermatter/weather-dashboard");
            project3.setLiveUrl("https://weather-dashboard-demo.netlify.app");
            project3.setDisplayOrder(3);

            projectRepository.saveAll(Arrays.asList(project1, project2, project3));
        }
    }
}
