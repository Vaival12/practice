package com.practiceSystem.dao.VacancyStatus;


import com.practiceSystem.Entity.VacancyStatus;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VacancyStatusRepository
        extends JpaRepository<VacancyStatus, Long> {


    VacancyStatus findByName(String name);

}