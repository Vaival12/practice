package com.practiceSystem.ApplicationServices;

import com.practiceSystem.Entity.Competency;
import com.practiceSystem.Entity.Direction;
import com.practiceSystem.dao.Competency.CompetencyRepository;
import com.practiceSystem.dao.Direction.DirectionRepository;
import com.practiceSystem.security.AccessService;
import org.springframework.stereotype.Service;
import com.practiceSystem.dao.Organization.OrganizationRepository;
import com.practiceSystem.dao.Organization.OrganizationService;
import com.practiceSystem.dto.request.OrganizationRequest;
import com.practiceSystem.Entity.Organization;

import java.util.List;
import java.util.Optional;



@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    private final AccessService accessService;

    private final CompetencyRepository competencyRepository;

    private final DirectionRepository directionRepository;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository, AccessService accessService, CompetencyRepository competencyRepository,         DirectionRepository directionRepository
    ) {

        this.organizationRepository = organizationRepository;
        this.accessService = accessService;
        this.competencyRepository = competencyRepository;
        this.directionRepository = directionRepository;

    }

    @Override
    public List<Organization> findAll() {

        return organizationRepository.findAll();

    }

    @Override
    public Optional<Organization> findById(Long id) {

        Organization organization = organizationRepository.findById(id).orElseThrow();


        if(!accessService.canViewOrganization(organization)){
            throw new RuntimeException("Нет доступа");
        }


        return Optional.of(organization);
    }

    @Override
    public Organization save(Organization organization) {

        return organizationRepository.save(organization);

    }

    @Override
    public void deleteById(Long id) {

        Organization organization = organizationRepository.findById(id).orElseThrow();


        if(!accessService.canEditOrganization(organization)){
            throw new RuntimeException("Нет доступа");
        }


        organizationRepository.delete(organization);

    }


    @Override
    public Organization create(OrganizationRequest request) {

        Organization organization = new Organization();

        organization.setName(request.getName());

        organization.setDescription(request.getDescription());
        organization.setAddress(request.getAddress());
        organization.setWebsite(request.getWebsite());
        organization.setEmail(request.getEmail());
        organization.setPhone(request.getPhone());

        List<Competency> competencies = competencyRepository.findAllById(request.getCompetencyIds());


        organization.setCompetencies(competencies);

        List<Direction> directions = directionRepository.findAllById(request.getDirectionIds());
        organization.setDirections(directions);

        return organizationRepository.save(organization);

    }

    @Override
    public Organization update(Long id, OrganizationRequest request) {

        Organization organization = organizationRepository.findById(id).orElseThrow();

        if(!accessService.canEditOrganization(organization)){
            throw new RuntimeException("Нет доступа");
        }

        organization.setName(request.getName());
        organization.setDescription(request.getDescription());
        organization.setAddress(request.getAddress());
        organization.setWebsite(request.getWebsite());
        organization.setEmail(request.getEmail());
        organization.setPhone(request.getPhone());

        List<Competency> competencies = competencyRepository.findAllById(request.getCompetencyIds());
        organization.setCompetencies(competencies);

        List<Direction> directions = directionRepository.findAllById(request.getDirectionIds());
        organization.setDirections(directions);

        return organizationRepository.save(organization);

    }
}