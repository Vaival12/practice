package com.practiceSystem.dao.Student;

import com.practiceSystem.Entity.University;
import com.practiceSystem.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.practiceSystem.Entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUser(User user);

    List<Student> findByUniversityAndApprovedFalse(University university);

    Optional<Student> findByUserId(Long userId);


}