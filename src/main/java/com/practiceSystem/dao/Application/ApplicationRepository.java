package com.practiceSystem.dao.Application;

import com.practiceSystem.Entity.Student;
import com.practiceSystem.Entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import com.practiceSystem.Entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByStudentId(Long studentId);

    long countByVacancyId(Long vacancyId);
}