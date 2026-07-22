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

        http.cors(cors -> {})
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

                        // AUTH
                        .requestMatchers("/api/auth/**")
                        .permitAll()

                        // REGISTRATION
                        .requestMatchers(HttpMethod.POST, "/api/users")
                        .permitAll()

                        // PUBLIC GET
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/directions/**"
                        )
                        .permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/competencies/**"
                        )
                        .permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/vacancies/**"
                        )
                        .permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/organizations/**"
                        )
                        .permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/universities/**"
                        )
                        .permitAll()


                        // USERS
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/users/me"
                        )
                        .authenticated()

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/users/me"
                        )
                        .authenticated()

                        .requestMatchers("/api/users/**")
                        .hasRole("ADMIN")


                                // STUDENTS

// Подать заявку
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/students/apply/**"
                                )
                                .hasRole("USER")

// Получить текущего студента
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/students/me"
                                )
                                .hasRole("STUDENT")

// Изменить текущего студента
                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/students/me"
                                )
                                .hasRole("STUDENT")

// Получить список студентов
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/students"
                                )
                                .hasAnyRole(
                                        "ADMIN",
                                        "UNIVERSITY_MODERATOR",
                                        "UNIVERSITY_SUPER_MODERATOR"
                                )

// Получить студента
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/students/*"
                                )
                                .hasAnyRole(
                                        "ADMIN",
                                        "UNIVERSITY_MODERATOR",
                                        "UNIVERSITY_SUPER_MODERATOR"
                                )

                                // Редактирование студента
                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/students/*"
                                )
                                .hasAnyRole(
                                        "ADMIN",
                                        "STUDENT"
                                )

                                // Удаление студента
                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/api/students/*"
                                )
                                .hasRole("ADMIN")

                                // Ожидающие заявки
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/students/pending"
                                )
                                .hasRole("UNIVERSITY_SUPER_MODERATOR")

                                // Одобрение студента
                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/students/*/approve"
                                )
                                .hasRole("UNIVERSITY_SUPER_MODERATOR")

                        // UNIVERSITIES

                        // Создание университета
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/universities"
                        )
                        .authenticated()

                        // Изменение конкретного университета
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/universities/*"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "UNIVERSITY_SUPER_MODERATOR"
                        )

                        // Удаление университета
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/universities/*"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "UNIVERSITY_SUPER_MODERATOR"
                        )

                        // Текущий университет
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/universities/me"
                        )
                        .hasAnyRole(
                                "UNIVERSITY_MODERATOR",
                                "UNIVERSITY_SUPER_MODERATOR"
                        )

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/universities/me"
                        )
                        .hasAnyRole(
                                "UNIVERSITY_MODERATOR",
                                "UNIVERSITY_SUPER_MODERATOR"
                        )

                        // Модераторы университетов
                        .requestMatchers(
                                "/api/universityModerators/**"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "UNIVERSITY_SUPER_MODERATOR"
                        )


                        // ORGANIZATIONS

                        // Создание организации
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/organizations"
                        )
                        .authenticated()

                        // Изменение конкретной организации
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/organizations/*"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "ORGANIZATION_SUPER_MODERATOR"
                        )

                        // Удаление организации
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/organizations/*"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "ORGANIZATION_SUPER_MODERATOR"
                        )

                        // Текущая организация
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/organizations/me"
                        )
                        .hasAnyRole(
                                "ORGANIZATION_MODERATOR",
                                "ORGANIZATION_SUPER_MODERATOR"
                        )

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/organizations/me"
                        )
                        .hasAnyRole(
                                "ORGANIZATION_MODERATOR",
                                "ORGANIZATION_SUPER_MODERATOR"
                        )

                        // Модераторы организаций
                        .requestMatchers(
                                "/api/organizationModerators/**"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "ORGANIZATION_SUPER_MODERATOR"
                        )


                        // VACANCIES
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/vacancies/**"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "ORGANIZATION_MODERATOR",
                                "ORGANIZATION_SUPER_MODERATOR"
                        )

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/vacancies/*/status"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "ORGANIZATION_MODERATOR",
                                "ORGANIZATION_SUPER_MODERATOR"
                        )

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/vacancies/*"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "ORGANIZATION_MODERATOR",
                                "ORGANIZATION_SUPER_MODERATOR"
                        )

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/vacancies/**"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "ORGANIZATION_MODERATOR",
                                "ORGANIZATION_SUPER_MODERATOR"
                        )


                        // APPLICATIONS
                        .requestMatchers(
                                "/api/applications/**"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "STUDENT",
                                "ORGANIZATION_MODERATOR",
                                "ORGANIZATION_SUPER_MODERATOR"
                        )


                        // DEFAULT
                        .anyRequest()
                        .authenticated()
                );

        return http.build();
    }
}