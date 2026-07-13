package com.practiceSystem.ApplicationServices;

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

    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {

        this.organizationRepository = organizationRepository;

    }

    @Override
    public List<Organization> findAll() {

        return organizationRepository.findAll();

    }

    @Override
    public Optional<Organization> findById(Long id) {

        return organizationRepository.findById(id);

    }

    @Override
    public Organization save(Organization organization) {

        return organizationRepository.save(organization);

    }

    @Override
    public void deleteById(Long id) {

        organizationRepository.deleteById(id);

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


        return organizationRepository.save(organization);

    }

    @Override
    public Organization update(Long id, OrganizationRequest request) {

        Organization organization = organizationRepository.findById(id).orElseThrow();

        organization.setName(request.getName());
        organization.setDescription(request.getDescription());
        organization.setAddress(request.getAddress());
        organization.setWebsite(request.getWebsite());
        organization.setEmail(request.getEmail());
        organization.setPhone(request.getPhone());

        return organizationRepository.save(organization);

    }
}