package com.practiceSystem.ApplicationServices;

import com.practiceSystem.ApplicationStatus.ApplicationStatusRepository;
import com.practiceSystem.Entity.Application;
import com.practiceSystem.Entity.ApplicationStatus;
import com.practiceSystem.Entity.Student;
import com.practiceSystem.Entity.Vacancy;
import com.practiceSystem.dao.Application.ApplicationRepository;
import com.practiceSystem.dao.Application.ApplicationService;
import com.practiceSystem.dao.Student.StudentRepository;
import com.practiceSystem.dao.Vacancy.VacancyRepository;
import com.practiceSystem.dto.request.ApplicationRequest;
import com.practiceSystem.security.AccessService;
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

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, StudentRepository studentRepository, VacancyRepository vacancyRepository, AccessService accessService,ApplicationStatusRepository statusRepository) {

        this.applicationRepository = applicationRepository;
        this.studentRepository = studentRepository;
        this.vacancyRepository = vacancyRepository;
        this.accessService = accessService;
        this.statusRepository = statusRepository;

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

        Student student = studentRepository.findById(request.getStudentId()).orElseThrow();

        Vacancy vacancy = vacancyRepository.findById(request.getVacancyId()).orElseThrow();

        ApplicationStatus status = statusRepository.findByName("APPLIED");

        Application application = new Application();

        application.setStudent(student);
        application.setVacancy(vacancy);
        application.setStatus(status);

        return applicationRepository.save(application);
    }

    @Override
    public Application updateStatus(Long id, ApplicationStatus status) {

        Application application = applicationRepository.findById(id).orElseThrow();

        if(!accessService.canEditApplication(application)){
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