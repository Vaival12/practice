package com.practiceSystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

                .cors(cors -> {})

                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authenticationProvider(authenticationProvider)

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .authorizeHttpRequests(auth -> auth

                        // Авторизация
                        .requestMatchers("/api/auth/**").permitAll()

                        // Регистрация
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()

                        // Публичный просмотр
                        .requestMatchers(HttpMethod.GET, "/api/directions/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/competencies/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/vacancies/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/organizations/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/universities/**").permitAll()

                        // Пользователи
                        .requestMatchers("/api/users/**")
                        .hasRole("ADMIN")

                        // Студенты
                        .requestMatchers("/api/students/**")
                        .hasAnyRole(
                                "ADMIN",
                                "STUDENT",
                                "UNIVERSITY_MODERATOR"
                        )

                        // Университеты
                        .requestMatchers("/api/universities/**")
                        .hasAnyRole(
                                "ADMIN",
                                "UNIVERSITY_MODERATOR"
                        )

                        // Организации
                        .requestMatchers("/api/organizations/**")
                        .hasAnyRole(
                                "ADMIN",
                                "ORGANIZATION_MODERATOR"
                        )

                        // Вакансии
                        .requestMatchers(HttpMethod.POST, "/api/vacancies/**")
                        .hasAnyRole(
                                "ADMIN",
                                "ORGANIZATION_MODERATOR"
                        )

                        .requestMatchers(HttpMethod.PUT, "/api/vacancies/**")
                        .hasAnyRole(
                                "ADMIN",
                                "ORGANIZATION_MODERATOR"
                        )

                        .requestMatchers(HttpMethod.DELETE, "/api/vacancies/**")
                        .hasAnyRole(
                                "ADMIN",
                                "ORGANIZATION_MODERATOR"
                        )

                        // Заявки
                        .requestMatchers("/api/applications/**")
                        .hasAnyRole(
                                "ADMIN",
                                "STUDENT",
                                "ORGANIZATION_MODERATOR"
                        )

                        // Модераторы
                        .requestMatchers("/api/universityModerators/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/organizationModerators/**")
                        .hasRole("ADMIN")

                        // Всё остальное
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}