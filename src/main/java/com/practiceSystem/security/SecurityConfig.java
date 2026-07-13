package com.practiceSystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          AuthenticationProvider authenticationProvider) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        // Доступ без авторизации
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()

                        // Администратор
                        .requestMatchers("/api/users/**").hasRole("ADMIN")

                        // Студенты
                        .requestMatchers("/api/students/**")
                        .hasAnyRole("ADMIN", "STUDENT")

                        // Университеты
                        .requestMatchers("/api/universities/**")
                        .hasAnyRole("ADMIN", "UNIVERSITY_MODERATOR")

                        .requestMatchers("/api/organizations/**")
                        .hasAnyRole("ADMIN", "ORGANIZATION_MODERATOR")

                        .requestMatchers("/api/vacancies/**")
                        .hasAnyRole("ADMIN", "ORGANIZATION_MODERATOR", "STUDENT")

                        .requestMatchers("/api/applications/**")
                        .hasAnyRole("ADMIN", "STUDENT", "ORGANIZATION_MODERATOR")

                        .requestMatchers("/api/universityModerators/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/organizationModerators/**")
                        .hasRole("ADMIN")

                        .anyRequest().authenticated()
                )

                .authenticationProvider(authenticationProvider)

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}