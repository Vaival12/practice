package com.practiceSystem.security;


import com.practiceSystem.Entity.Role;
import com.practiceSystem.dao.Role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataInitializer implements CommandLineRunner {


    private final RoleRepository roleRepository;


    public DataInitializer(RoleRepository roleRepository) {

        this.roleRepository = roleRepository;

    }



    @Override
    public void run(String... args) {

        createRole("USER");

        createRole("STUDENT");

        createRole("ADMIN");

        createRole("UNIVERSITY_MODERATOR");

        createRole("UNIVERSITY_SUPER_MODERATOR");

        createRole("ORGANIZATION_MODERATOR");

        createRole("ORGANIZATION_SUPER_MODERATOR");

    }



    private void createRole(String name) {


        if(roleRepository.findByName(name).isEmpty()) {


            Role role = new Role();

            role.setName(name);

            roleRepository.save(role);

        }

    }

}