package com.practiceSystem.ApplicationServices;

import com.practiceSystem.Entity.Role;
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

    @Override
    public void checkUniversitySuperModerator() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow();

        if (user.getRole().getName().equals("ORGANIZATION_SUPER_MODERATOR")) {
            throw new RuntimeException("Недостаточно прав.");
        }
    }

    @Override
    public void checkOrganizationSuperModerator() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow();

        if (user.getRole().getName().equals("ORGANIZATION_SUPER_MODERATOR")) {
            throw new RuntimeException("Недостаточно прав.");
        }
    }

    @Override
    public void checkUniversityModerator() {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();


        User user =
                userRepository.findByEmail(authentication.getName())
                        .orElseThrow();


        if(user.getRole().getName().equals("UNIVERSITY_SUPER_MODERATOR") &&
                user.getRole().getName().equals("ORGANIZATION_SUPER_MODERATOR") &&
                user.getRole().getName().equals("ADMIN")) {


            throw new RuntimeException(
                    "Недостаточно прав"
            );
        }

    }

    @Override
    public void checkOrganizationModerator() {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();


        User user =
                userRepository.findByEmail(authentication.getName())
                        .orElseThrow();


        if(user.getRole().getName().equals("UNIVERSITY_SUPER_MODERATOR") &&
                user.getRole().getName().equals("ORGANIZATION_SUPER_MODERATOR") &&
                user.getRole().getName().equals("ADMIN")) {


            throw new RuntimeException(
                    "Недостаточно прав"
            );
        }

    }
}