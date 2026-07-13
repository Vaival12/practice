package com.practiceSystem.dao.Application;

import org.springframework.data.jpa.repository.JpaRepository;
import com.practiceSystem.Entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

}