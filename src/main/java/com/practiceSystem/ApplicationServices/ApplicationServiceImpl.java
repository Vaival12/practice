package com.practiceSystem.ApplicationServices;

import com.practiceSystem.Entity.*;
import com.practiceSystem.dao.ApplicationStatus.ApplicationStatusRepository;
import com.practiceSystem.dao.Application.ApplicationRepository;
import com.practiceSystem.dao.Application.ApplicationService;
import com.practiceSystem.dao.Student.StudentRepository;
import com.practiceSystem.dao.User.UserRepository;
import com.practiceSystem.dao.Vacancy.VacancyRepository;
import com.practiceSystem.dto.request.ApplicationRequest;
import com.practiceSystem.security.AccessService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentRepository studentRepository;
    private final VacancyRepository vacancyRepository;
    private final AccessService accessService;
    private final ApplicationStatusRepository statusRepository;
    private final UserRepository userRepository;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, StudentRepository studentRepository, VacancyRepository vacancyRepository, AccessService accessService, ApplicationStatusRepository statusRepository, UserRepository userRepository) {

        this.applicationRepository = applicationRepository;
        this.studentRepository = studentRepository;
        this.vacancyRepository = vacancyRepository;
        this.accessService = accessService;
        this.statusRepository = statusRepository;

        this.userRepository = userRepository;
    }

    @Override
    public List<Application> findAll() {

        return applicationRepository.findAll();

    }

    @Override
    public Optional<Application> findById(Long id) {

        Application application =
                applicationRepository.findById(id)
                        .orElseThrow();


        if(!accessService.canViewApplication(application)){
            throw new RuntimeException("Нет доступа");
        }


        return Optional.of(application);

    }

    @Override
    public Application save(Application application) {

        return applicationRepository.save(application);

    }

    @Override
    public void deleteById(Long id) {

        applicationRepository.deleteById(id);

    }

    @Override
    public Application create(ApplicationRequest request) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();


        User user =
                userRepository
                        .findByEmail(authentication.getName())
                        .orElseThrow(() ->
                                new RuntimeException("Пользователь не найден")
                        );


        Student student =
                studentRepository
                        .findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Профиль студента не найден"
                                )
                        );


        Vacancy vacancy =
                vacancyRepository
                        .findById(request.getVacancyId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Вакансия не найдена"
                                )
                        );

        boolean alreadyApplied = applicationRepository.existsByStudentId(student.getId());

        if (alreadyApplied) {
            throw new RuntimeException("Студент уже подал заявку на вакансию");
        }

        long applicationsCount = applicationRepository.countByVacancyId(vacancy.getId());

        if (vacancy.getMaxStudents() != null && applicationsCount >= vacancy.getMaxStudents()) {
            throw new RuntimeException("На вакансию уже набрано максимальное количество студентов");}

        ApplicationStatus status = statusRepository.findByName("APPLIED");


        if (status == null) {
            throw new RuntimeException("Статус APPLIED не найден");
        }


        Application application = new Application();

        application.setStudent(student);

        application.setVacancy(vacancy);

        application.setStatus(status);

        application.setComment(request.getComment());


        return applicationRepository.save(application);
    }

    @Override
    public Application updateStatus(Long id, ApplicationStatus status) {

        Application application =
                applicationRepository.findById(id)
                        .orElseThrow();

        if (!accessService.canEditApplicationStatus(application)) {
            throw new RuntimeException("Нет доступа");
        }

        application.setStatus(status);

        return applicationRepository.save(application);
    }

    @Override
    public Application update(Long id, ApplicationRequest request) {

        Application application = applicationRepository.findById(id).orElseThrow();

        Student student = studentRepository.findById(request.getStudentId()).orElseThrow();

        Vacancy vacancy = vacancyRepository.findById(request.getVacancyId()).orElseThrow();

        application.setStudent(student);
        application.setVacancy(vacancy);
        application.setComment(request.getComment());

        return applicationRepository.save(application);
    }
}