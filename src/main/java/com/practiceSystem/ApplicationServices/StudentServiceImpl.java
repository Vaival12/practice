package com.practiceSystem.ApplicationServices;

import com.practiceSystem.Entity.*;
import com.practiceSystem.dao.Competency.CompetencyRepository;
import com.practiceSystem.dao.Direction.DirectionRepository;
import com.practiceSystem.dao.University.UniversityRepository;
import com.practiceSystem.dao.User.UserRepository;
import com.practiceSystem.security.AccessService;
import org.springframework.stereotype.Service;
import com.practiceSystem.dao.Student.StudentRepository;
import com.practiceSystem.dao.Student.StudentService;
import com.practiceSystem.dto.request.StudentRequest;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final UserRepository userRepository;

    private final UniversityRepository universityRepository;

    private final AccessService accessService;

    private final CompetencyRepository competencyRepository;

    private final DirectionRepository directionRepository;

    public StudentServiceImpl(StudentRepository studentRepository, UserRepository userRepository, UniversityRepository universityRepository, AccessService accessService,CompetencyRepository competencyRepository, DirectionRepository directionRepository) {

        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.universityRepository = universityRepository;
        this.accessService = accessService;
        this.competencyRepository = competencyRepository;
        this.directionRepository = directionRepository;

    }

    @Override
    public List<Student> findAll() {

        return studentRepository.findAll();

    }

    @Override
    public Optional<Student> findById(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow();


        if (!accessService.canViewStudent(student)) {
            throw new RuntimeException("Нет доступа");
        }


        return Optional.of(student);
    }

    @Override
    public Student save(Student student) {

        return studentRepository.save(student);

    }

    @Override
    public void deleteById(Long id) {

        Student student = studentRepository.findById(id).orElseThrow();


        if (!accessService.canEditStudent(student)) {
            throw new RuntimeException("Нет доступа");
        }


        studentRepository.delete(student);
    }

    @Override
    public Student create(StudentRequest request) {

        User user = userRepository.findById(request.getUserId()).orElseThrow();

        University university = universityRepository.findById(request.getUniversityId()).orElseThrow();

        Student student = new Student();

        student.setUser(user);
        student.setUniversity(university);
        student.setCourse(request.getCourse());
        student.setGroupName(request.getGroupName());
        student.setSpecialization(request.getSpecialization());
        student.setPracticeStart(request.getPracticeStart());
        student.setPracticeEnd(request.getPracticeEnd());

        List<Competency> competencies = competencyRepository.findAllById(request.getCompetencyIds());
        student.setCompetencies(competencies);

        List<Direction> directions = directionRepository.findAllById(request.getDirectionIds());
        student.setDirections(directions);

        return studentRepository.save(student);

    }

    @Override
    public Student update(Long id, StudentRequest request) {

        Student student = studentRepository.findById(id).orElseThrow();

        if (!accessService.canEditStudent(student)) {
            throw new RuntimeException("Нет доступа");
        }

        User user = userRepository.findById(request.getUserId()).orElseThrow();

        University university = universityRepository.findById(request.getUniversityId()).orElseThrow();

        student.setUser(user);
        student.setUniversity(university);
        student.setCourse(request.getCourse());
        student.setGroupName(request.getGroupName());
        student.setSpecialization(request.getSpecialization());
        student.setPracticeStart(request.getPracticeStart());
        student.setPracticeEnd(request.getPracticeEnd());

        List<Competency> competencies = competencyRepository.findAllById(request.getCompetencyIds());
        student.setCompetencies(competencies);

        List<Direction> directions = directionRepository.findAllById(request.getDirectionIds());
        student.setDirections(directions);

        return studentRepository.save(student);
    }

}