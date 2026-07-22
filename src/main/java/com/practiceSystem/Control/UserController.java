package com.practiceSystem.Control;

import org.springframework.web.bind.annotation.*;
import com.practiceSystem.dto.request.UserRequest;
import com.practiceSystem.dto.response.UserResponse;
import com.practiceSystem.Entity.User;
import com.practiceSystem.dao.User.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest request) {

        User user = service.create(request);

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setMiddleName(user.getMiddleName());
        response.setBirthDate(user.getBirthDate());
        response.setPhone(user.getPhone());
        response.setActive(user.getActive());

        return response;
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {

        User user = service.findById(id).orElseThrow();

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setMiddleName(user.getMiddleName());
        response.setBirthDate(user.getBirthDate());
        response.setPhone(user.getPhone());
        response.setActive(user.getActive());

        return response;
    }

    @GetMapping
    public List<UserResponse> getAll() {

        List<UserResponse> list = new ArrayList<>();

        for (User user : service.findAll()) {

            UserResponse response = new UserResponse();

            response.setId(user.getId());
            response.setEmail(user.getEmail());
            response.setRole(user.getRole());
            response.setFirstName(user.getFirstName());
            response.setLastName(user.getLastName());
            response.setMiddleName(user.getMiddleName());
            response.setBirthDate(user.getBirthDate());
            response.setPhone(user.getPhone());
            response.setActive(user.getActive());

            list.add(response);
        }

        return list;
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @RequestBody UserRequest request) {

        User user = service.update(id, request);

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setMiddleName(user.getMiddleName());
        response.setBirthDate(user.getBirthDate());
        response.setPhone(user.getPhone());
        response.setActive(user.getActive());

        return response;
    }

    @GetMapping("/me")
    public UserResponse me() {

        User user = service.getCurrentUser();

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setMiddleName(user.getMiddleName());
        response.setBirthDate(user.getBirthDate());
        response.setPhone(user.getPhone());
        response.setActive(user.getActive());

        return response;
    }

    @PutMapping("/me")
    public UserResponse updateMe(@RequestBody UserRequest request) {

        User user = service.updateCurrentUser(request);

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setMiddleName(user.getMiddleName());
        response.setBirthDate(user.getBirthDate());
        response.setPhone(user.getPhone());
        response.setActive(user.getActive());

        return response;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}