package com.akalber.alankalbermatter.repository;

import com.akalber.alankalbermatter.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    
    List<Skill> findByCategoryOrderByDisplayOrderAsc(String category);
    
    List<Skill> findByIsFeaturedTrueOrderByDisplayOrderAsc();
    
    List<Skill> findAllByOrderByCategoryAscDisplayOrderAsc();
    
    List<Skill> findByNameContainingIgnoreCase(String name);
    
    List<Skill> findByLevel(Integer level);
}
