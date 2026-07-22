package com.practiceSystem.dao.Control;

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

    public UniversityModeratorController(UniversityModeratorService service){
        this.service = service;
    }


    @PostMapping
    public UniversityModeratorResponse create(@RequestBody UniversityModeratorRequest request){

        UniversityModerator moderator = service.create(request);

        UniversityModeratorResponse response = new UniversityModeratorResponse();

        response.setId(moderator.getId());
        response.setUniversityId(moderator.getUniversity().getId());
        response.setUserId(moderator.getUser().getId());

        return response;
    }


    @GetMapping("/{id}")
    public UniversityModeratorResponse getById(@PathVariable Long id){

        UniversityModerator moderator = service.findById(id).orElseThrow();

        UniversityModeratorResponse response = new UniversityModeratorResponse();

        response.setId(moderator.getId());
        response.setUniversityId(moderator.getUniversity().getId());
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