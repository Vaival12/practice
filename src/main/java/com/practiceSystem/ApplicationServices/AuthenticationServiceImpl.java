package com.practiceSystem.ApplicationServices;

import com.practiceSystem.dao.Authentication.AuthenticationService;
import com.practiceSystem.dao.User.UserRepository;
import com.practiceSystem.Entity.User;
import com.practiceSystem.dto.request.LoginRequest;
import com.practiceSystem.security.JwtService;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(UserRepository userRepository, JwtService jwtService,PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Неверный пароль");
        }

        return jwtService.generateToken(user.getEmail());
    }
}