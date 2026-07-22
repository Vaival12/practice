package com.practiceSystem.Control;

import com.practiceSystem.Entity.UniversityModerator;
import com.practiceSystem.dao.UniversityModerator.UniversityModeratorService;
import com.practiceSystem.dto.response.UniversityModeratorResponse;
import org.springframework.web.bind.annotation.*;
import com.practiceSystem.dto.request.UniversityRequest;
import com.practiceSystem.dto.response.UniversityResponse;
import com.practiceSystem.Entity.University;
import com.practiceSystem.dao.University.UniversityService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/universities")
public class UniversityController {

    private final UniversityService universityService;

    private final UniversityModeratorService moderatorService;

    public UniversityController(UniversityService universityService, UniversityModeratorService moderatorService) {

        this.universityService = universityService;
        this.moderatorService = moderatorService;

    }

    @PostMapping
    public UniversityResponse create(@RequestBody UniversityRequest request) {

        University university = universityService.create(request);

        UniversityResponse response = new UniversityResponse();

        response.setId(university.getId());
        response.setName(university.getName());
        response.setDescription(university.getDescription());
        response.setAddress(university.getAddress());
        response.setWebsite(university.getWebsite());
        response.setEmail(university.getEmail());
        response.setPhone(university.getPhone());

        return response;

    }

    @GetMapping("/{id}")
    public UniversityResponse getById(@PathVariable Long id) {

        University university = universityService.findById(id).orElseThrow();

        UniversityResponse response = new UniversityResponse();

        response.setId(university.getId());
        response.setName(university.getName());
        response.setDescription(university.getDescription());
        response.setAddress(university.getAddress());
        response.setWebsite(university.getWebsite());
        response.setEmail(university.getEmail());
        response.setPhone(university.getPhone());

        return response;

    }

    @GetMapping
    public List<UniversityResponse> getAll(){

        List<UniversityResponse> result = new ArrayList<>();

        for(University university : universityService.findAll()) {

            UniversityResponse response = new UniversityResponse();

            response.setId(university.getId());
            response.setName(university.getName());
            response.setDescription(university.getDescription());
            response.setAddress(university.getAddress());
            response.setWebsite(university.getWebsite());
            response.setEmail(university.getEmail());
            response.setPhone(university.getPhone());
            result.add(response);

        }

        return result;

    }

    @PutMapping("/{id}")
    public UniversityResponse update(@PathVariable Long id, @RequestBody UniversityRequest request) {

        University university = universityService.update(id, request);
        UniversityResponse response = new UniversityResponse();

        response.setId(university.getId());
        response.setName(university.getName());
        response.setDescription(university.getDescription());
        response.setAddress(university.getAddress());
        response.setWebsite(university.getWebsite());
        response.setEmail(university.getEmail());
        response.setPhone(university.getPhone());

        return response;

    }

    @GetMapping("/me")
    public UniversityResponse me() {

        University university =
                universityService.getCurrentUniversity();

        UniversityResponse response = new UniversityResponse();

        response.setId(university.getId());
        response.setName(university.getName());
        response.setDescription(university.getDescription());
        response.setAddress(university.getAddress());
        response.setWebsite(university.getWebsite());
        response.setEmail(university.getEmail());
        response.setPhone(university.getPhone());

        return response;
    }

    @PutMapping("/me")
    public UniversityResponse updateMe(@RequestBody UniversityRequest request){

        University university = universityService.updateCurrentUniversity(request);

        UniversityResponse response = new UniversityResponse();

        response.setId(university.getId());
        response.setName(university.getName());
        response.setDescription(university.getDescription());
        response.setAddress(university.getAddress());
        response.setWebsite(university.getWebsite());
        response.setEmail(university.getEmail());
        response.setPhone(university.getPhone());

        return response;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){

        universityService.deleteById(id);

    }
}