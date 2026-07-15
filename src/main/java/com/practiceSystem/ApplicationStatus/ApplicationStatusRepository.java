package com.practiceSystem.ApplicationStatus;

import com.practiceSystem.Entity.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatus, Long> {


    ApplicationStatus findByName(String name);

}