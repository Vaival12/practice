package com.practiceSystem.ApplicationServices;

import com.practiceSystem.Entity.User;
import com.practiceSystem.dao.User.UserRepository;
import com.practiceSystem.dao.Permission.PermissionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class PermissionServiceImpl implements PermissionService {


    private final UserRepository userRepository;

    public PermissionServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User getCurrentUser(){

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();


        return userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    @Override
    public void checkUniversitySuperModerator() {

        User user = getCurrentUser();

        if(!user.getRole().getName().equals("UNIVERSITY_SUPER_MODERATOR")){
            throw new RuntimeException("Недостаточно прав");
        }

    }


    @Override
    public void checkOrganizationSuperModerator() {

        User user = getCurrentUser();

        if(!user.getRole().getName().equals("ORGANIZATION_SUPER_MODERATOR")){
            throw new RuntimeException("Недостаточно прав");
        }

    }


    @Override
    public void checkUniversityModerator() {

        User user = getCurrentUser();

        String role = user.getRole().getName();

        if(!role.equals("UNIVERSITY_MODERATOR") && !role.equals("UNIVERSITY_SUPER_MODERATOR") && !role.equals("ADMIN")){
            throw new RuntimeException("Недостаточно прав");
        }

    }


    @Override
    public void checkOrganizationModerator() {

        User user = getCurrentUser();

        String role = user.getRole().getName();

        if(!role.equals("ORGANIZATION_MODERATOR") && !role.equals("ORGANIZATION_SUPER_MODERATOR") && !role.equals("ADMIN")){
            throw new RuntimeException("Недостаточно прав");
        }

    }

}