package com.practiceSystem.Control;

import org.springframework.web.bind.annotation.*;
import com.practiceSystem.Entity.Student;
import com.practiceSystem.dto.request.StudentRequest;
import com.practiceSystem.dto.response.StudentResponse;
import com.practiceSystem.dao.Student.StudentService;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service){ this.service = service; }


    @PostMapping
    public StudentResponse create(@RequestBody StudentRequest request){

        Student student = service.create(request);

        StudentResponse response = new StudentResponse();

        response.setId(student.getId());
        response.setGroupName(student.getGroupName());
        response.setSpecialization(student.getSpecialization());
        response.setUniversityName(student.getUniversity().getName());
        response.setCourse(student.getCourse());
        response.setPracticeStart(student.getPracticeStart());
        response.setPracticeEnd(student.getPracticeEnd());

        return response;
    }


    @GetMapping("/{id}")
    public StudentResponse getById(@PathVariable Long id){

        Student student = service.findById(id).orElseThrow();

        StudentResponse response = new StudentResponse();

        response.setId(student.getId());
        response.setCourse(student.getCourse());
        response.setGroupName(student.getGroupName());
        response.setSpecialization(student.getSpecialization());
        response.setPracticeStart(student.getPracticeStart());
        response.setPracticeEnd(student.getPracticeEnd());

        if(student.getUniversity() != null){
            response.setUniversityName(student.getUniversity().getName());
        }

        return response;
    }


    @GetMapping
    public List<StudentResponse> getAll(){

        List<StudentResponse> list = new ArrayList<>();

        for(Student student : service.findAll()){

            StudentResponse response = new StudentResponse();

            response.setId(student.getId());
            response.setCourse(student.getCourse());
            response.setGroupName(student.getGroupName());
            response.setSpecialization(student.getSpecialization());
            response.setUniversityName(student.getUniversity().getName());
            response.setPracticeStart(student.getPracticeStart());
            response.setPracticeEnd(student.getPracticeEnd());

            list.add(response);
        }

        return list;
    }

    @PutMapping("/{id}")
    public StudentResponse update(@PathVariable Long id,@RequestBody StudentRequest request){

        Student student = service.update(id,request);

        StudentResponse response = new StudentResponse();

        response.setId(student.getId());

        response.setGroupName(student.getGroupName());
        response.setSpecialization(student.getSpecialization());
        response.setUniversityName(student.getUniversity().getName());
        response.setCourse(student.getCourse());
        response.setUniversityName(student.getUniversity().getName());
        response.setPracticeStart(student.getPracticeStart());
        response.setPracticeEnd(student.getPracticeEnd());

        return response;
    }

    @GetMapping("/me")
    public StudentResponse me() {

        Student student = service.getCurrentStudent();

        StudentResponse response = new StudentResponse();

        response.setId(student.getId());
        response.setGroupName(student.getGroupName());
        response.setSpecialization(student.getSpecialization());
        response.setUniversityName(student.getUniversity().getName());
        response.setCourse(student.getCourse());
        response.setPracticeStart(student.getPracticeStart());
        response.setPracticeEnd(student.getPracticeEnd());

        return response;
    }

    @PutMapping("/me")
    public StudentResponse updateMe(@RequestBody StudentRequest request) {

        Student student = service.updateCurrentStudent(request);

        StudentResponse response = new StudentResponse();

        response.setId(student.getId());
        response.setGroupName(student.getGroupName());
        response.setSpecialization(student.getSpecialization());
        response.setUniversityName(student.getUniversity().getName());
        response.setCourse(student.getCourse());
        response.setPracticeStart(student.getPracticeStart());
        response.setPracticeEnd(student.getPracticeEnd());

        return response;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){ service.deleteById(id); }

    @PostMapping("/apply/{universityId}")
    public StudentResponse apply(@PathVariable Long universityId) {

        Student student = service.apply(universityId);

        StudentResponse response = new StudentResponse();

        response.setId(student.getId());
        response.setUserId(student.getUser().getId());
        response.setUniversityId(student.getUniversity().getId());
        response.setApproved(student.getApproved());

        return response;
    }

    @GetMapping("/pending")
    public List<StudentResponse> getPending() {

        List<StudentResponse> response = new ArrayList<>();

        for (Student student : service.getPending()) {

            StudentResponse item = new StudentResponse();

            item.setId(student.getId());
            item.setUserId(student.getUser().getId());
            item.setUniversityId(student.getUniversity().getId());
            item.setApproved(student.getApproved());

            response.add(item);
        }

        return response;
    }

    @PutMapping("/{id}/approve")
    public StudentResponse approve(@PathVariable Long id) {

        Student student = service.approve(id);

        StudentResponse response = new StudentResponse();

        response.setId(student.getId());
        response.setUserId(student.getUser().getId());
        response.setUniversityId(student.getUniversity().getId());
        response.setApproved(student.getApproved());

        return response;
    }

}