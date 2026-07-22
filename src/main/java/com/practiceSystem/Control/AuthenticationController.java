package com.practiceSystem.dao.Control;

import com.practiceSystem.dao.Authentication.AuthenticationService;
import com.practiceSystem.dto.request.LoginRequest;
import com.practiceSystem.dto.response.LoginResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        String token = service.login(request);

        LoginResponse response = new LoginResponse();
        response.setToken(token);

        return response;
    }
}