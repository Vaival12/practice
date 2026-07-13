package com.practiceSystem.dao.University;

import com.practiceSystem.Entity.UniversityModerator;
import org.springframework.data.jpa.repository.JpaRepository;
import com.practiceSystem.Entity.University;

import java.util.List;
import java.util.Optional;

public interface UniversityRepository extends JpaRepository<University, Long> {

}