package com.practiceSystem.Control;

import com.practiceSystem.Entity.UniversityModerator;
import com.practiceSystem.dao.UniversityModerator.UniversityModeratorService;
import com.practiceSystem.dto.request.UniversityModeratorRequest;
import com.practiceSystem.dto.response.UniversityModeratorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/universityModerators")
public class UniversityModeratorController {

    private final UniversityModeratorService service;
    private final UniversityModeratorService moderatorService;

    public UniversityModeratorController(UniversityModeratorService service, UniversityModeratorService moderatorService){
        this.service = service;
        this.moderatorService = moderatorService;
    }


    @PostMapping
    public UniversityModeratorResponse create(@RequestBody UniversityModeratorRequest request){

        UniversityModerator moderator = service.create(request);

        UniversityModeratorResponse response = new UniversityModeratorResponse();

        response.setId(moderator.getId());
        response.setUniversityId(moderator.getUniversity().getId());
        response.setEmail(moderator.getUser().getEmail());
        response.setFirstName(moderator.getUser().getFirstName());
        response.setLastName(moderator.getUser().getLastName());
        response.setApproved(moderator.getApproved());
        response.setUserId(moderator.getUser().getId());

        return response;
    }


    @GetMapping("/{id}")
    public UniversityModeratorResponse getById(@PathVariable Long id){

        UniversityModerator moderator = service.findById(id).orElseThrow();

        UniversityModeratorResponse response = new UniversityModeratorResponse();

        response.setId(moderator.getId());
        response.setUniversityId(moderator.getUniversity().getId());
        response.setEmail(moderator.getUser().getEmail());
        response.setFirstName(moderator.getUser().getFirstName());
        response.setLastName(moderator.getUser().getLastName());
        response.setApproved(moderator.getApproved());
        response.setUserId(moderator.getUser().getId());

        return response;
    }


    @GetMapping
    public List<UniversityModeratorResponse> getAll(){

        List<UniversityModeratorResponse> list = new ArrayList<>();

        for(UniversityModerator moderator : service.findAll()){

            UniversityModeratorResponse response = new UniversityModeratorResponse();

            response.setId(moderator.getId());
            response.setUniversityId(moderator.getUniversity().getId());
            response.setEmail(moderator.getUser().getEmail());
            response.setFirstName(moderator.getUser().getFirstName());
            response.setLastName(moderator.getUser().getLastName());
            response.setApproved(moderator.getApproved());
            response.setUserId(moderator.getUser().getId());

            list.add(response);
        }

        return list;
    }


    @GetMapping("/university/{id}")
    public List<UniversityModeratorResponse> getByUniversity(@PathVariable Long id){

        List<UniversityModeratorResponse> list = new ArrayList<>();

        for(UniversityModerator moderator : service.findByUniversityId(id)){

            UniversityModeratorResponse response = new UniversityModeratorResponse();

            response.setId(moderator.getId());
            response.setUniversityId(moderator.getUniversity().getId());
            response.setEmail(moderator.getUser().getEmail());
            response.setFirstName(moderator.getUser().getFirstName());
            response.setLastName(moderator.getUser().getLastName());
            response.setApproved(moderator.getApproved());
            response.setUserId(moderator.getUser().getId());

            list.add(response);
        }

        return list;
    }

    @PutMapping("/{id}")
    public UniversityModeratorResponse update(
            @PathVariable Long id,
            @RequestBody UniversityModeratorRequest request) {

        UniversityModerator moderator = service.update(id, request);

        UniversityModeratorResponse response = new UniversityModeratorResponse();

        response.setId(moderator.getId());
        response.setUniversityId(moderator.getUniversity().getId());
        response.setEmail(moderator.getUser().getEmail());
        response.setFirstName(moderator.getUser().getFirstName());
        response.setLastName(moderator.getUser().getLastName());
        response.setApproved(moderator.getApproved());
        response.setUserId(moderator.getUser().getId());

        return response;
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){

        service.deleteById(id);

    }

    @PutMapping("/{id}/approve")
    public UniversityModeratorResponse approve(@PathVariable Long id) {

        UniversityModerator moderator = service.approve(id);

        UniversityModeratorResponse response = new UniversityModeratorResponse();

        response.setId(moderator.getId());
        response.setApproved(moderator.getApproved());


        return response;
    }

    @GetMapping("/pending")
    public List<UniversityModeratorResponse> getPendingModerators() {

        List<UniversityModerator> moderators = moderatorService.getPendingModerators();

        List<UniversityModeratorResponse> response = new ArrayList<>();

        for (UniversityModerator moderator : moderators) {

            UniversityModeratorResponse item = new UniversityModeratorResponse();

            item.setId(moderator.getId());
            item.setApproved(moderator.getApproved());

            item.setUserId(moderator.getUser().getId());
            item.setFirstName(moderator.getUser().getFirstName());
            item.setLastName(moderator.getUser().getLastName());
            item.setEmail(moderator.getUser().getEmail());

            response.add(item);
        }

        return response;
    }
}