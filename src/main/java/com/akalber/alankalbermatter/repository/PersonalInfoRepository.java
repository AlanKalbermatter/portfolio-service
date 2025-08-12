package com.akalber.alankalbermatter.repository;

import com.akalber.alankalbermatter.model.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long> {
    // Para información personal, generalmente solo habrá un registro
}
