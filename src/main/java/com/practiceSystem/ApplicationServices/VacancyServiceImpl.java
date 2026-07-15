package com.practiceSystem.ApplicationServices;

import com.practiceSystem.Entity.*;
import com.practiceSystem.dao.Competency.CompetencyRepository;
import com.practiceSystem.dao.Direction.DirectionRepository;
import com.practiceSystem.dao.Organization.OrganizationRepository;
import com.practiceSystem.dao.Vacancy.VacancyRepository;
import com.practiceSystem.dao.Vacancy.VacancyService;
import com.practiceSystem.dao.VacancyStatus.VacancyStatusRepository;
import com.practiceSystem.dto.request.VacancyRequest;
import com.practiceSystem.security.AccessService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;

    private final OrganizationRepository organizationRepository;

    private final AccessService accessService;

    private final VacancyStatusRepository statusRepository;

    private final CompetencyRepository competencyRepository;

    private final DirectionRepository directionRepository;

    public VacancyServiceImpl(VacancyRepository vacancyRepository, OrganizationRepository organizationRepository,AccessService accessService, VacancyStatusRepository statusRepository, CompetencyRepository competencyRepository, DirectionRepository directionRepository) {

        this.vacancyRepository = vacancyRepository;
        this.organizationRepository = organizationRepository;
        this.accessService = accessService;
        this.statusRepository = statusRepository;
        this.competencyRepository = competencyRepository;
        this.directionRepository = directionRepository;

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

        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(() -> new RuntimeException("Вакансия не найдена"));

        if (!accessService.canEditVacancy(vacancy)) {
            throw new RuntimeException("Нет доступа");
        }

        vacancyRepository.delete(vacancy);

    }

    @Override
    public Vacancy create(VacancyRequest request) {

        Organization organization = organizationRepository.findById(request.getOrganizationId()).orElseThrow();

        Vacancy vacancy = new Vacancy();

        List<Competency> competencies = competencyRepository.findAllById(request.getCompetencyIds());
        vacancy.setCompetencies(competencies);

        List<Direction> directions = directionRepository.findAllById(request.getDirectionIds());
        vacancy.setDirections(directions);

        vacancy.setOrganization(organization);
        vacancy.setTitle(request.getTitle());
        vacancy.setDescription(request.getDescription());
        vacancy.setRequirements(request.getRequirements());
        vacancy.setDirection(request.getDirection());
        vacancy.setPracticeResult(request.getPracticeResult());
        VacancyStatus status = statusRepository.findByName("OPEN");
        vacancy.setStatus(status);
        vacancy.setPracticeStart(request.getPracticeStart());
        vacancy.setPracticeEnd(request.getPracticeEnd());


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
        vacancy.setPracticeStart(request.getPracticeStart());
        vacancy.setPracticeEnd(request.getPracticeEnd());

        List<Competency> competencies = competencyRepository.findAllById(request.getCompetencyIds());
        vacancy.setCompetencies(competencies);

        List<Direction> directions = directionRepository.findAllById(request.getDirectionIds());
        vacancy.setDirections(directions);

        if(!accessService.canEditVacancy(vacancy)){
            throw new RuntimeException("Нет доступа");
        }

        return vacancyRepository.save(vacancy);

    }

    @Override
    public Vacancy updateStatus(Long id, VacancyStatus status){

        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow();

        vacancy.setStatus(status);

        return vacancyRepository.save(vacancy);
    }
}