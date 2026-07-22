package com.practiceSystem.security;

import com.practiceSystem.Entity.*;

public interface AccessService {

    boolean canViewStudent(Student student);

    boolean canEditStudent(Student student);

    boolean canViewVacancy(Vacancy vacancy);

    boolean canEditVacancy(Vacancy vacancy);

    boolean canViewApplication(Application application);

    boolean canEditApplication(Application application);

    boolean canViewOrganization(Organization organization);

    boolean canEditOrganization(Organization organization);

    boolean canViewUniversity(University university);

    boolean canEditUniversity(University university);

    boolean canCreateApplication(Student student);

    boolean canDeleteApplication(Application application);

    boolean canEditApplicationStatus(Application application);
}