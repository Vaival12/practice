package com.practiceSystem.dao.Vacancy;

import org.springframework.data.jpa.repository.JpaRepository;
import com.practiceSystem.Entity.Vacancy;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

}