package com.practiceSystem.Control;


import com.practiceSystem.Entity.VacancyStatus;
import org.springframework.web.bind.annotation.*;
import com.practiceSystem.Entity.Vacancy;
import com.practiceSystem.dto.request.VacancyRequest;
import com.practiceSystem.dto.response.VacancyResponse;
import com.practiceSystem.dao.Vacancy.VacancyService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/vacancies")
public class VacancyController {

    private final VacancyService vacancyService;

    public VacancyController(VacancyService vacancyService) {

        this.vacancyService = vacancyService;

    }

    @PostMapping
    public VacancyResponse create(@RequestBody VacancyRequest request) {

        Vacancy vacancy = vacancyService.create(request);

        VacancyResponse response = new VacancyResponse();

        response.setId(vacancy.getId());
        response.setTitle(vacancy.getTitle());
        response.setDescription(vacancy.getDescription());
        response.setRequirements(vacancy.getRequirements());
        response.setDirection(vacancy.getDirection());
        response.setPracticeResult(vacancy.getPracticeResult());
        response.setStatus(vacancy.getStatus().getName());
        response.setPracticeStart(vacancy.getPracticeStart());
        response.setPracticeEnd(vacancy.getPracticeEnd());

        return response;

    }

    @GetMapping("/{id}")
    public VacancyResponse getById(@PathVariable Long id) {

        Vacancy vacancy = vacancyService.findById(id).orElseThrow();

        VacancyResponse response = new VacancyResponse();

        response.setId(vacancy.getId());
        response.setTitle(vacancy.getTitle());
        response.setDescription(vacancy.getDescription());
        response.setRequirements(vacancy.getRequirements());
        response.setDirection(vacancy.getDirection());
        response.setPracticeResult(vacancy.getPracticeResult());
        response.setStatus(vacancy.getStatus().getName());
        response.setOrganizationName(vacancy.getOrganization().getName());
        response.setCreatedAt(vacancy.getCreatedAt());
        response.setPracticeStart(vacancy.getPracticeStart());
        response.setPracticeEnd(vacancy.getPracticeEnd());

        return response;

    }

    @GetMapping
    public List<VacancyResponse> getAll(){

        List<VacancyResponse> result = new ArrayList<>();

        for(Vacancy vacancy : vacancyService.findAll()) {
            VacancyResponse response = new VacancyResponse();

            response.setId(vacancy.getId());
            response.setTitle(vacancy.getTitle());
            response.setDescription(vacancy.getDescription());
            response.setRequirements(vacancy.getRequirements());
            response.setDirection(vacancy.getDirection());
            response.setPracticeResult(vacancy.getPracticeResult());
            response.setStatus(vacancy.getStatus().getName());
            response.setOrganizationName(vacancy.getOrganization().getName());
            response.setCreatedAt(vacancy.getCreatedAt());
            response.setPracticeStart(vacancy.getPracticeStart());
            response.setPracticeEnd(vacancy.getPracticeEnd());

            result.add(response);

        }

        return result;

    }

    @PutMapping("/{id}")
    public VacancyResponse update(@PathVariable Long id, @RequestBody VacancyRequest request){

        Vacancy vacancy = vacancyService.update(id, request);

        VacancyResponse response = new VacancyResponse();

        response.setId(vacancy.getId());
        response.setTitle(vacancy.getTitle());
        response.setDescription(vacancy.getDescription());
        response.setRequirements(vacancy.getRequirements());
        response.setDirection(vacancy.getDirection());
        response.setPracticeResult(vacancy.getPracticeResult());
        response.setStatus(vacancy.getStatus().getName());
        response.setOrganizationName(vacancy.getOrganization().getName());
        response.setCreatedAt(vacancy.getCreatedAt());
        response.setPracticeStart(vacancy.getPracticeStart());
        response.setPracticeEnd(vacancy.getPracticeEnd());

        return response;

    }

    @PutMapping("/{id}/status")
    public VacancyResponse updateStatus(@PathVariable Long id, @RequestParam VacancyStatus status){

        Vacancy vacancy = vacancyService.updateStatus(id, status);

        VacancyResponse response = new VacancyResponse();

        response.setId(vacancy.getId());
        response.setStatus(vacancy.getStatus().getName());

        return response;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){

        vacancyService.deleteById(id);

    }

}