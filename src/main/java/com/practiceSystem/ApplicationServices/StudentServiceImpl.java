package com.practiceSystem.ApplicationServices;

import com.practiceSystem.Entity.*;
import com.practiceSystem.dao.Competency.CompetencyRepository;
import com.practiceSystem.dao.Direction.DirectionRepository;
import com.practiceSystem.dao.Role.RoleRepository;
import com.practiceSystem.dao.University.UniversityRepository;
import com.practiceSystem.dao.UniversitySuperModeratorRepository.UniversitySuperModeratorRepository;
import com.practiceSystem.dao.User.UserRepository;
import com.practiceSystem.security.AccessService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UniversitySuperModeratorRepository superModeratorRepository;
    private final RoleRepository roleRepository;

    public StudentServiceImpl(StudentRepository studentRepository, UserRepository userRepository, UniversityRepository universityRepository, AccessService accessService, CompetencyRepository competencyRepository, DirectionRepository directionRepository, UniversitySuperModeratorRepository superModeratorRepository, RoleRepository roleRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.universityRepository = universityRepository;
        this.accessService = accessService;
        this.competencyRepository = competencyRepository;
        this.directionRepository = directionRepository;
        this.superModeratorRepository = superModeratorRepository;
        this.roleRepository = roleRepository;
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

    @Override
    public Student getCurrentStudent() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        return studentRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Студент не найден"));
    }

    @Override
    public Student updateCurrentStudent(StudentRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        Student student = studentRepository.findByUser(user)
                .orElseThrow();

        student.setGroupName(request.getGroupName());
        student.setCourse(request.getCourse());
        student.setPracticeStart(request.getPracticeStart());
        student.setPracticeEnd(request.getPracticeEnd());

        if (request.getUniversityId() != null) {
            University university = universityRepository
                    .findById(request.getUniversityId())
                    .orElseThrow();

            student.setUniversity(university);
        }

        return studentRepository.save(student);
    }

    @Override
    public Student apply(Long universityId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        University university = universityRepository.findById(universityId)
                .orElseThrow(() -> new RuntimeException("Университет не найден"));

        Optional<Student> existingStudent =
                studentRepository.findByUserId(user.getId());

        if (existingStudent.isPresent()) {
            throw new RuntimeException("Заявка уже существует");
        }

        Student student = new Student();

        student.setUser(user);
        student.setUniversity(university);
        student.setApproved(false);

        return studentRepository.save(student);
    }

    @Override
    public List<Student> getPending() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow();

        UniversitySuperModerator superModerator =
                superModeratorRepository.findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException("Супермодератор не найден"));

        University university = superModerator.getUniversity();

        return studentRepository.findByUniversityAndApprovedFalse(university);
    }

    @Override
    public Student approve(Long id) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow();

        UniversitySuperModerator superModerator =
                superModeratorRepository.findByUser(currentUser)
                        .orElseThrow(() ->
                                new RuntimeException("Супермодератор не найден"));

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Студент не найден"));

        if (!student.getUniversity().getId()
                .equals(superModerator.getUniversity().getId())) {

            throw new RuntimeException(
                    "Студент относится к другому университету"
            );
        }

        student.setApproved(true);

        Role studentRole = roleRepository.findByName("STUDENT")
                .orElseThrow(() ->
                        new RuntimeException("Роль STUDENT не найдена"));

        User user = student.getUser();
        user.setRole(studentRole);

        userRepository.save(user);

        return studentRepository.save(student);
    }

}