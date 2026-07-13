package com.practiceSystem.ApplicationServices;

import com.practiceSystem.Entity.Vacancy;
import com.practiceSystem.Entity.Organization;
import com.practiceSystem.Entity.VacancyStatus;
import com.practiceSystem.dao.Organization.OrganizationRepository;
import com.practiceSystem.dao.Vacancy.VacancyRepository;
import com.practiceSystem.dao.Vacancy.VacancyService;
import com.practiceSystem.dto.request.VacancyRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;

    private final OrganizationRepository organizationRepository;

    public VacancyServiceImpl(VacancyRepository vacancyRepository, OrganizationRepository organizationRepository) {

        this.vacancyRepository = vacancyRepository;
        this.organizationRepository = organizationRepository;

    }

    @Override
    public List<Vacancy> findAll() {

        return vacancyRepository.findAll();

    }

    @Override
    public Optional<Vacancy> findById(Long id) {

        return vacancyRepository.findById(id);

    }

    @Override
    public Vacancy save(Vacancy vacancy) {

        return vacancyRepository.save(vacancy);

    }

    @Override
    public void deleteById(Long id) {

        vacancyRepository.deleteById(id);

    }

    @Override
    public Vacancy create(VacancyRequest request) {

        Organization organization = organizationRepository.findById(request.getOrganizationId()).orElseThrow();

        Vacancy vacancy = new Vacancy();

        vacancy.setOrganization(organization);
        vacancy.setTitle(request.getTitle());
        vacancy.setDescription(request.getDescription());
        vacancy.setRequirements(request.getRequirements());
        vacancy.setDirection(request.getDirection());
        vacancy.setPracticeResult(request.getPracticeResult());
        vacancy.setStatus(VacancyStatus.OPEN);

        return vacancyRepository.save(vacancy);

    }

    @Override
    public Vacancy update(Long id, VacancyRequest request) {

        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow();

        vacancy.setTitle(request.getTitle());
        vacancy.setDescription(request.getDescription());
        vacancy.setRequirements(request.getRequirements());
        vacancy.setDirection(request.getDirection());
        vacancy.setPracticeResult(request.getPracticeResult());

        return vacancyRepository.save(vacancy);

    }

    @Override
    public Vacancy updateStatus(Long id, VacancyStatus status){

        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow();

        vacancy.setStatus(status);

        return vacancyRepository.save(vacancy);
    }
}