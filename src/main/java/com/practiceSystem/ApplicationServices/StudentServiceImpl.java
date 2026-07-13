package com.practiceSystem.ApplicationServices;

import com.practiceSystem.dao.University.UniversityRepository;
import com.practiceSystem.dao.User.UserRepository;
import org.springframework.stereotype.Service;
import com.practiceSystem.dao.Student.StudentRepository;
import com.practiceSystem.dao.Student.StudentService;
import com.practiceSystem.Entity.University;
import com.practiceSystem.Entity.User;
import com.practiceSystem.Entity.Student;
import com.practiceSystem.dto.request.StudentRequest;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final UserRepository userRepository;

    private final UniversityRepository universityRepository;

    public StudentServiceImpl(StudentRepository studentRepository, UserRepository userRepository, UniversityRepository universityRepository) {

        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.universityRepository = universityRepository;

    }

    @Override
    public List<Student> findAll() {

        return studentRepository.findAll();

    }

    @Override
    public Optional<Student> findById(Long id) {

        return studentRepository.findById(id);

    }

    @Override
    public Student save(Student student) {

        return studentRepository.save(student);

    }

    @Override
    public void deleteById(Long id) {

        studentRepository.deleteById(id);

    }

    @Override
    public Student create(StudentRequest request) {

        User user = userRepository.findById(request.getUserId()).orElseThrow();

        University university = universityRepository.findById(request.getUniversityId()).orElseThrow();

        Student student = new Student();

        student.setUser(user);
        student.setUniversity(university);
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setMiddleName(request.getMiddleName());
        student.setBirthDate(request.getBirthDate());
        student.setPhone(request.getPhone());
        student.setCourse(request.getCourse());
        student.setGroupName(request.getGroupName());
        student.setSpecialization(request.getSpecialization());

        return studentRepository.save(student);

    }

    @Override
    public Student update(Long id, StudentRequest request) {

        Student student = studentRepository.findById(id).orElseThrow();

        User user = userRepository.findById(request.getUserId()).orElseThrow();

        University university = universityRepository.findById(request.getUniversityId()).orElseThrow();

        student.setUser(user);
        student.setUniversity(university);

        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setMiddleName(request.getMiddleName());
        student.setBirthDate(request.getBirthDate());
        student.setPhone(request.getPhone());
        student.setCourse(request.getCourse());
        student.setGroupName(request.getGroupName());
        student.setSpecialization(request.getSpecialization());

        return studentRepository.save(student);
    }

}