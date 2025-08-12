package com.akalber.alankalbermatter.repository;

import com.akalber.alankalbermatter.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByIsFeaturedTrueOrderByDisplayOrderAsc();

    List<Project> findAllByOrderByDisplayOrderAsc();

    @Query("SELECT p FROM Project p WHERE p.technologies LIKE %:technology%")
    List<Project> findByTechnology(String technology);

    List<Project> findByTitleContainingIgnoreCase(String title);
}
