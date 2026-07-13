package com.practiceSystem.dao.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import com.practiceSystem.Entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}