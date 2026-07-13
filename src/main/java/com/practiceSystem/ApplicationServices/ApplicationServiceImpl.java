package com.practiceSystem.ApplicationServices;

import com.practiceSystem.Entity.Application;
import com.practiceSystem.Entity.ApplicationStatus;
import com.practiceSystem.Entity.Student;
import com.practiceSystem.Entity.Vacancy;
import com.practiceSystem.dao.Application.ApplicationRepository;
import com.practiceSystem.dao.Application.ApplicationService;
import com.practiceSystem.dao.Student.StudentRepository;
import com.practiceSystem.dao.Vacancy.VacancyRepository;
import com.practiceSystem.dto.request.ApplicationRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentRepository studentRepository;
    private final VacancyRepository vacancyRepository;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, StudentRepository studentRepository, VacancyRepository vacancyRepository) {

        this.applicationRepository = applicationRepository;
        this.studentRepository = studentRepository;
        this.vacancyRepository = vacancyRepository;

    }

    @Override
    public List<Application> findAll() {

        return applicationRepository.findAll();

    }

    @Override
    public Optional<Application> findById(Long id) {

        return applicationRepository.findById(id);

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

        Student student = studentRepository.findById(request.getStudentId()).orElseThrow();

        Vacancy vacancy = vacancyRepository.findById(request.getVacancyId()).orElseThrow();

        Application application = new Application();

        application.setStudent(student);
        application.setVacancy(vacancy);
        application.setComment(request.getComment());
        application.setStatus(ApplicationStatus.APPLIED);

        return applicationRepository.save(application);
    }

    @Override
    public Application updateStatus(Long id, ApplicationStatus status) {

        Application application = applicationRepository.findById(id).orElseThrow();

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