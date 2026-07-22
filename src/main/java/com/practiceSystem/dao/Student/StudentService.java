package com.practiceSystem.dao.Student;

import com.practiceSystem.Entity.Student;
import com.practiceSystem.dao.base.BaseService;
import com.practiceSystem.dto.request.StudentRequest;

import java.util.List;

public interface StudentService extends BaseService<Student, Long> {

    Student create(StudentRequest request);

    Student update(Long id, StudentRequest request);

    Student getCurrentStudent();

    Student updateCurrentStudent(StudentRequest request);

    Student apply(Long universityId);

    List<Student> getPending();

    Student approve(Long id);
}