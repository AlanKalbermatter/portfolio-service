package com.akalber.alankalbermatter.repository;

import com.akalber.alankalbermatter.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    
    List<Experience> findAllByOrderByStartDateDesc();
    
    List<Experience> findByIsCurrentTrueOrderByStartDateDesc();
    
    List<Experience> findByCompanyContainingIgnoreCase(String company);
    
    List<Experience> findByPositionContainingIgnoreCase(String position);
}
