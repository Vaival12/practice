package com.practiceSystem.ApplicationServices;

import com.practiceSystem.Entity.*;
import com.practiceSystem.dto.request.OrganizationModeratorRequest;
import org.springframework.stereotype.Service;
import com.practiceSystem.dao.OrganizationModerator.OrganizationModeratorRepository;
import com.practiceSystem.dao.OrganizationModerator.OrganizationModeratorService;
import com.practiceSystem.dao.Organization.OrganizationRepository;
import com.practiceSystem.dao.User.UserRepository;
import com.practiceSystem.dao.Permission.PermissionService;

import java.util.List;
import java.util.Optional;


@Service
public class OrganizationModeratorServiceImpl implements OrganizationModeratorService {

    private final OrganizationModeratorRepository moderatorRepository;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final PermissionService permissionService;

    public OrganizationModeratorServiceImpl(OrganizationModeratorRepository moderatorRepository, OrganizationRepository organizationRepository, UserRepository userRepository, PermissionService permissionService) {

        this.moderatorRepository = moderatorRepository;
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
        this.permissionService = permissionService;

    }

    @Override
    public OrganizationModerator create(OrganizationModeratorRequest request) {

        permissionService.checkOrganizationSuperModerator();

        Organization organization = organizationRepository.findById(request.getOrganizationId()).orElseThrow();


        User user = userRepository.findById(request.getUserId()).orElseThrow();

        OrganizationModerator moderator = new OrganizationModerator();

        moderator.setOrganization(organization);
        moderator.setUser(user);

        return moderatorRepository.save(moderator);
    }

    @Override
    public List<OrganizationModerator> findAll() {

        permissionService.checkOrganizationModerator();

        return moderatorRepository.findAll();

    }

    @Override
    public Optional<OrganizationModerator> findById(Long id) {

        permissionService.checkOrganizationModerator();

        return moderatorRepository.findById(id);

    }

    @Override
    public List<OrganizationModerator> findByOrganizationId(Long organizationId) {

        permissionService.checkOrganizationModerator();

        return moderatorRepository.findByOrganizationId(organizationId);

    }

    @Override
    public OrganizationModerator save(OrganizationModerator moderator) {

        permissionService.checkOrganizationSuperModerator();

        return moderatorRepository.save(moderator);

    }

    @Override
    public void deleteById(Long id) {

        permissionService.checkOrganizationSuperModerator();

        moderatorRepository.deleteById(id);

    }
}