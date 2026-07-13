package com.practiceSystem.Control;

import org.springframework.web.bind.annotation.*;
import com.practiceSystem.dto.request.OrganizationRequest;
import com.practiceSystem.dto.response.OrganizationResponse;
import com.practiceSystem.Entity.Organization;
import com.practiceSystem.dao.Organization.OrganizationService;
import com.practiceSystem.Entity.OrganizationModerator;
import com.practiceSystem.dao.OrganizationModerator.OrganizationModeratorService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    private final OrganizationModeratorService moderatorService;

    public OrganizationController(OrganizationService organizationService, OrganizationModeratorService moderatorService) {

        this.organizationService = organizationService;
        this.moderatorService = moderatorService;

    }

    @PostMapping
    public OrganizationResponse create(@RequestBody OrganizationRequest request) {

        Organization organization = organizationService.create(request);
        OrganizationResponse response = new OrganizationResponse();
        response.setId(organization.getId());
        response.setName(organization.getName());
        response.setDescription(organization.getDescription());
        response.setAddress(organization.getAddress());
        response.setWebsite(organization.getWebsite());

        return response;
    }

    @GetMapping("/{id}")
    public OrganizationResponse getById(@PathVariable Long id) {

        Organization organization = organizationService.findById(id).orElseThrow();
        OrganizationResponse response = new OrganizationResponse();
        response.setId(organization.getId());
        response.setName(organization.getName());
        response.setDescription(organization.getDescription());
        response.setAddress(organization.getAddress());
        response.setWebsite(organization.getWebsite());

        return response;

    }

    @GetMapping
    public List<OrganizationResponse> getAll(){

        List<OrganizationResponse> result = new ArrayList<>();

        for(Organization organization :
                organizationService.findAll()) {

            OrganizationResponse response = new OrganizationResponse();
            response.setId(organization.getId());
            response.setName(organization.getName());
            response.setDescription(organization.getDescription());
            response.setAddress(organization.getAddress());
            response.setWebsite(organization.getWebsite());
            result.add(response);
        }

        return result;

    }

    @PutMapping("/{id}")
    public OrganizationResponse update(@PathVariable Long id, @RequestBody OrganizationRequest request){

        Organization organization = organizationService.update(id, request);
        OrganizationResponse response = new OrganizationResponse();
        response.setId(organization.getId());
        response.setName(organization.getName());
        response.setDescription(organization.getDescription());
        response.setAddress(organization.getAddress());
        response.setWebsite(organization.getWebsite());

        return response;

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){

        organizationService.deleteById(id);

    }

}