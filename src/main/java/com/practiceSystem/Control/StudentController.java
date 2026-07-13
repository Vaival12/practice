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
        response.setFirstName(student.getFirstName());
        response.setLastName(student.getLastName());
        response.setMiddleName(student.getMiddleName());
        response.setBirthDate(student.getBirthDate());
        response.setGroupName(student.getGroupName());
        response.setSpecialization(student.getSpecialization());
        response.setUniversityName(student.getUniversity().getName());
        response.setPhone(student.getPhone());
        response.setCourse(student.getCourse());

        return response;
    }


    @GetMapping("/{id}")
    public StudentResponse getById(@PathVariable Long id){

        Student student = service.findById(id).orElseThrow();

        StudentResponse response = new StudentResponse();

        response.setId(student.getId());
        response.setFirstName(student.getFirstName());
        response.setLastName(student.getLastName());
        response.setMiddleName(student.getMiddleName());
        response.setBirthDate(student.getBirthDate());
        response.setPhone(student.getPhone());
        response.setCourse(student.getCourse());
        response.setGroupName(student.getGroupName());
        response.setSpecialization(student.getSpecialization());

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
            response.setFirstName(student.getFirstName());
            response.setLastName(student.getLastName());
            response.setMiddleName(student.getMiddleName());
            response.setBirthDate(student.getBirthDate());
            response.setPhone(student.getPhone());
            response.setCourse(student.getCourse());
            response.setGroupName(student.getGroupName());
            response.setSpecialization(student.getSpecialization());
            response.setUniversityName(student.getUniversity().getName());

            list.add(response);
        }

        return list;
    }

    @PutMapping("/{id}")
    public StudentResponse update(@PathVariable Long id,@RequestBody StudentRequest request){

        Student student = service.update(id,request);

        StudentResponse response = new StudentResponse();

        response.setId(student.getId());
        response.setFirstName(student.getFirstName());
        response.setLastName(student.getLastName());
        response.setMiddleName(student.getMiddleName());
        response.setBirthDate(student.getBirthDate());
        response.setGroupName(student.getGroupName());
        response.setSpecialization(student.getSpecialization());
        response.setUniversityName(student.getUniversity().getName());
        response.setPhone(student.getPhone());
        response.setCourse(student.getCourse());
        response.setUniversityName(student.getUniversity().getName());

        return response;
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){ service.deleteById(id); }

}