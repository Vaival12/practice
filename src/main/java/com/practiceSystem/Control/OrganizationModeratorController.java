package com.practiceSystem.Control;

import com.practiceSystem.Entity.OrganizationModerator;
import com.practiceSystem.dao.OrganizationModerator.OrganizationModeratorService;
import com.practiceSystem.dto.request.OrganizationModeratorRequest;
import com.practiceSystem.dto.response.OrganizationModeratorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/organizationModerators")
public class OrganizationModeratorController {

    private final OrganizationModeratorService service;

    public OrganizationModeratorController(OrganizationModeratorService service){
        this.service = service;
    }


    @PostMapping
    public OrganizationModeratorResponse create(@RequestBody OrganizationModeratorRequest request){

        OrganizationModerator moderator = service.create(request);

        OrganizationModeratorResponse response = new OrganizationModeratorResponse();

        response.setId(moderator.getId());
        response.setOrganizationId(moderator.getOrganization().getId());
        response.setUserId(moderator.getUser().getId());

        return response;
    }

    @GetMapping
    public List<OrganizationModeratorResponse> getAll(){

        List<OrganizationModeratorResponse> list = new ArrayList<>();

        for(OrganizationModerator moderator : service.findAll()){

            OrganizationModeratorResponse response = new OrganizationModeratorResponse();

            response.setId(moderator.getId());
            response.setOrganizationId(moderator.getOrganization().getId());
            response.setUserId(moderator.getUser().getId());

            list.add(response);
        }

        return list;
    }

    @GetMapping("/{id}")
    public OrganizationModeratorResponse getById(@PathVariable Long id){

        OrganizationModerator moderator = service.findById(id).orElseThrow();

        OrganizationModeratorResponse response = new OrganizationModeratorResponse();

        response.setId(moderator.getId());
        response.setOrganizationId(moderator.getOrganization().getId());
        response.setUserId(moderator.getUser().getId());

        return response;
    }


    @GetMapping("/organization/{id}")
    public List<OrganizationModeratorResponse> getByOrganization(@PathVariable Long id){

        List<OrganizationModeratorResponse> list = new ArrayList<>();

        for(OrganizationModerator moderator : service.findByOrganizationId(id)){

            OrganizationModeratorResponse response = new OrganizationModeratorResponse();

            response.setId(moderator.getId());
            response.setOrganizationId(moderator.getOrganization().getId());
            response.setUserId(moderator.getUser().getId());

            list.add(response);
        }

        return list;
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.deleteById(id);
    }
}