package com.practiceSystem.dao.Authentication;

import com.practiceSystem.dto.request.LoginRequest;

public interface AuthenticationService {

    String login(LoginRequest request);

}