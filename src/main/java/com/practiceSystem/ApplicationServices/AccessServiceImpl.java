package com.practiceSystem.ApplicationServices;

import com.practiceSystem.Entity.*;
import com.practiceSystem.dao.OrganizationModerator.OrganizationModeratorRepository;
import com.practiceSystem.dao.User.UserRepository;
import com.practiceSystem.security.AccessService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class AccessServiceImpl implements AccessService {


    private final UserRepository userRepository;
    private final OrganizationModeratorRepository moderatorRepository;

    public AccessServiceImpl(UserRepository userRepository, OrganizationModeratorRepository moderatorRepository) {
        this.userRepository = userRepository;
        this.moderatorRepository = moderatorRepository;
    }


    private User getCurrentUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByEmail(authentication.getName()).orElseThrow();
    }



    @Override
    public boolean canViewStudent(Student student){

        User user = getCurrentUser();

        if(user.getRole().getName().equals("ADMIN")){
            return true;
        }


        if(user.getRole().getName().equals("STUDENT")){
            return student.getUser().getId().equals(user.getId());
        }

        return false;
    }

    @Override
    public boolean canEditStudent(Student student){

        User user = getCurrentUser();

        if(user.getRole().getName().equals("ADMIN")){
            return true;
        }

        if(user.getRole().getName().equals("STUDENT")){

            return student.getUser().getId().equals(user.getId());
        }

        return false;
    }


    @Override
    public boolean canViewApplication(Application application){

        User user = getCurrentUser();

        if(user.getRole().getName().equals("ADMIN")){
            return true;
        }

        if(user.getRole().getName().equals("STUDENT")){

            return application.getStudent()
                    .getUser()
                    .getId()
                    .equals(user.getId());
        }


        if(user.getRole().getName().equals("UNIVERSITY_SUPER_MODERATOR") ||
                user.getRole().getName().equals("UNIVERSITY_MODERATOR")){


            return application.getVacancy().getOrganization().getModerators().stream().anyMatch(moderator -> moderator.getUser().getId().equals(user.getId()));
        }


        return false;
    }

    @Override
    public boolean canViewVacancy(Vacancy vacancy){

        User user = getCurrentUser();

        return true;
    }

    @Override
    public boolean canEditVacancy(Vacancy vacancy) {

        User user = getCurrentUser();

        if (user.getRole().getName().equals("ADMIN")) {return true;}

        if (user.getRole().getName().equals("ORGANIZATION_SUPER_MODERATOR") || user.getRole().getName().equals("ORGANIZATION_MODERATOR")) {
            return moderatorRepository.findByOrganizationIdAndUserId(vacancy.getOrganization().getId(), user.getId()).isPresent();
        }

        return false;
    }

    @Override
    public boolean canEditApplication(Application application) {

        User user = getCurrentUser();

        if (user.getRole().getName().equals("ADMIN")) {return true;}
        if (user.getRole().getName().equals("STUDENT")) {return false;}
        if (user.getRole().getName().equals("ORGANIZATION_SUPER_MODERATOR") || user.getRole().getName().equals("ORGANIZATION_MODERATOR")) {
            return application.getVacancy().getOrganization().getModerators().stream().anyMatch(moderator -> moderator.getUser().getId().equals(user.getId()));
        }

        return false;
    }

    @Override
    public boolean canViewOrganization(Organization organization) {

        User user = getCurrentUser();


        if (user.getRole().getName().equals("ADMIN")) {
            return true;
        }


        return true;
    }

    @Override
    public boolean canEditOrganization(Organization organization) {

        User user = getCurrentUser();

        if (user.getRole().getName().equals("ADMIN")) {return true;}
        if (user.getRole().getName().equals("ORGANIZATION_SUPER_MODERATOR") || user.getRole().getName().equals("ORGANIZATION_MODERATOR")) {
            return moderatorRepository.findByOrganizationIdAndUserId(organization.getId(), user.getId()).isPresent();
        }

        return false;
    }

    @Override
    public boolean canViewUniversity(University university) {

        User user = getCurrentUser();


        if(user.getRole().getName().equals("ADMIN")){
            return true;
        }


        return true;
    }

    @Override
    public boolean canEditUniversity(University university) {

        User user = getCurrentUser();


        if(user.getRole().getName().equals("ADMIN")){
            return true;
        }


        if(user.getRole().getName().equals("UNIVERSITY_SUPER_MODERATOR") ||
                user.getRole().getName().equals("UNIVERSITY_MODERATOR")){


            return university.getModerators()
                    .stream()
                    .anyMatch(
                            moderator ->
                                    moderator.getUser()
                                            .getId()
                                            .equals(user.getId())
                    );
        }


        return false;
    }
}