package com.practiceSystem.dao.Permission;


public interface PermissionService {

    void checkUniversitySuperModerator();

    void checkOrganizationSuperModerator();

    void checkUniversityModerator();

    void checkOrganizationModerator();

}