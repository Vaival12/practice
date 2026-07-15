package com.practiceSystem.Control;

import com.practiceSystem.Entity.ApplicationStatus;
import org.springframework.web.bind.annotation.*;
import com.practiceSystem.Entity.Application;
import com.practiceSystem.dto.request.ApplicationRequest;
import com.practiceSystem.dto.response.ApplicationResponse;
import com.practiceSystem.dao.Application.ApplicationService;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService service;

    public ApplicationController(ApplicationService service){ this.service = service; }


    @PostMapping
    public ApplicationResponse create(@RequestBody ApplicationRequest request){

        Application application = service.create(request);

        ApplicationResponse response = new ApplicationResponse();

        response.setId(application.getId());
        response.setStatus(application.getStatus().getName());
        response.setStudentName(application.getStudent().getUser().getFirstName());
        response.setVacancyTitle(application.getVacancy().getTitle());
        response.setComment(application.getComment());
        response.setCreatedAt(application.getCreatedAt());

        return response;
    }


    @GetMapping("/{id}")
    public ApplicationResponse getById(@PathVariable Long id){

        Application application = service.findById(id).orElseThrow();

        ApplicationResponse response = new ApplicationResponse();

        response.setId(application.getId());
        response.setStatus(application.getStatus().getName());
        response.setStudentName(application.getStudent().getUser().getFirstName());
        response.setVacancyTitle(application.getVacancy().getTitle());
        response.setComment(application.getComment());
        response.setCreatedAt(application.getCreatedAt());

        return response;
    }


    @GetMapping
    public List<ApplicationResponse> getAll(){

        List<ApplicationResponse> list = new ArrayList<>();

        for(Application application : service.findAll()){

            ApplicationResponse response = new ApplicationResponse();

            response.setId(application.getId());
            response.setStatus(application.getStatus().getName());
            response.setStudentName(application.getStudent().getUser().getFirstName());
            response.setVacancyTitle(application.getVacancy().getTitle());
            response.setComment(application.getComment());
            response.setCreatedAt(application.getCreatedAt());

            list.add(response);
        }

        return list;
    }


    @PutMapping("/{id}/status")
    public ApplicationResponse updateStatus(@PathVariable Long id, @RequestParam ApplicationStatus status){

        Application application = service.updateStatus(id,status);

        ApplicationResponse response = new ApplicationResponse();

        response.setId(application.getId());
        response.setStatus(application.getStatus().getName());
        response.setComment(application.getComment());

        return response;
    }

    @PutMapping("/{id}")
    public ApplicationResponse update(@PathVariable Long id,@RequestBody ApplicationRequest request){

        Application application = service.update(id,request);

        ApplicationResponse response = new ApplicationResponse();

        response.setId(application.getId());
        response.setStatus(application.getStatus().getName());
        response.setStudentName(application.getStudent().getUser().getFirstName());
        response.setVacancyTitle(application.getVacancy().getTitle());
        response.setComment(application.getComment());
        response.setCreatedAt(application.getCreatedAt());

        return response;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){ service.deleteById(id); }

}