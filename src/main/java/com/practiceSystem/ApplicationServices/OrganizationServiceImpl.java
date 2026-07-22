package com.practiceSystem.ApplicationServices;

import com.practiceSystem.Entity.*;
import com.practiceSystem.dao.Competency.CompetencyRepository;
import com.practiceSystem.dao.Direction.DirectionRepository;
import com.practiceSystem.dao.OrganizationModerator.OrganizationModeratorRepository;
import com.practiceSystem.dao.OrganizationSuperModerator.OrganizationSuperModeratorRepository;
import com.practiceSystem.dao.Role.RoleRepository;
import com.practiceSystem.dao.User.UserRepository;
import com.practiceSystem.security.AccessService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.practiceSystem.dao.Organization.OrganizationRepository;
import com.practiceSystem.dao.Organization.OrganizationService;
import com.practiceSystem.dto.request.OrganizationRequest;

import java.util.List;
import java.util.Optional;



@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final AccessService accessService;
    private final CompetencyRepository competencyRepository;
    private final DirectionRepository directionRepository;
    private final UserRepository userRepository;
    private final OrganizationModeratorRepository moderatorRepository;
    private final OrganizationSuperModeratorRepository superModeratorRepository;
    private final RoleRepository roleRepository;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository, AccessService accessService, CompetencyRepository competencyRepository, DirectionRepository directionRepository, UserRepository userRepository, OrganizationModeratorRepository moderatorRepository, OrganizationSuperModeratorRepository superModeratorRepository, RoleRepository roleRepository
    ) {

        this.organizationRepository = organizationRepository;
        this.accessService = accessService;
        this.competencyRepository = competencyRepository;
        this.directionRepository = directionRepository;

        this.userRepository = userRepository;
        this.moderatorRepository = moderatorRepository;
        this.superModeratorRepository = superModeratorRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Organization> findAll() {

        return organizationRepository.findAll();

    }

    @Override
    public Optional<Organization> findById(Long id) {

        Organization organization = organizationRepository.findById(id).orElseThrow();


        if(!accessService.canViewOrganization(organization)){
            throw new RuntimeException("Нет доступа");
        }


        return Optional.of(organization);
    }

    @Override
    public Organization save(Organization organization) {

        return organizationRepository.save(organization);

    }

    @Override
    public void deleteById(Long id) {

        Organization organization = organizationRepository.findById(id).orElseThrow();


        if(!accessService.canEditOrganization(organization)){
            throw new RuntimeException("Нет доступа");
        }


        organizationRepository.delete(organization);

    }


    @Override
    public Organization create(OrganizationRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email).orElseThrow();

        Organization organization = new Organization();

        organization.setName(request.getName());
        organization.setDescription(request.getDescription());
        organization.setAddress(request.getAddress());
        organization.setWebsite(request.getWebsite());
        organization.setEmail(request.getEmail());
        organization.setPhone(request.getPhone());


        if (request.getCompetencyIds() != null) {
            List<Competency> competencies = competencyRepository.findAllById(request.getCompetencyIds());
            organization.setCompetencies(competencies);
        }

        if (request.getDirectionIds() != null) {
            List<Direction> directions = directionRepository.findAllById(request.getDirectionIds());

            organization.setDirections(directions);
        }

        Organization savedOrganization = organizationRepository.save(organization);

        Role superModeratorRole = roleRepository.findByName("ORGANIZATION_SUPER_MODERATOR").orElseThrow(() ->
                new RuntimeException("Роль ORGANIZATION_SUPER_MODERATOR не найдена"));

        currentUser.setRole(superModeratorRole);

        userRepository.save(currentUser);

        OrganizationSuperModerator superModerator = new OrganizationSuperModerator();

        superModerator.setUser(currentUser);
        superModerator.setOrganization(savedOrganization);

        superModeratorRepository.save(superModerator);

        return savedOrganization;
    }

    @Override
    public Organization update(Long id, OrganizationRequest request) {

        Organization organization = organizationRepository.findById(id).orElseThrow();

        if(!accessService.canEditOrganization(organization)){
            throw new RuntimeException("Нет доступа");
        }

        organization.setName(request.getName());
        organization.setDescription(request.getDescription());
        organization.setAddress(request.getAddress());
        organization.setWebsite(request.getWebsite());
        organization.setEmail(request.getEmail());
        organization.setPhone(request.getPhone());

        List<Competency> competencies = competencyRepository.findAllById(request.getCompetencyIds());
        organization.setCompetencies(competencies);

        List<Direction> directions = directionRepository.findAllById(request.getDirectionIds());
        organization.setDirections(directions);

        return organizationRepository.save(organization);

    }

    @Override
    public Organization updateCurrentOrganization(OrganizationRequest request){

        Organization organization = getCurrentOrganization();

        organization.setName(request.getName());
        organization.setDescription(request.getDescription());
        organization.setAddress(request.getAddress());
        organization.setWebsite(request.getWebsite());
        organization.setEmail(request.getEmail());
        organization.setPhone(request.getPhone());

        return organizationRepository.save(organization);
    }

    @Override
    public Organization getCurrentOrganization() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow();


        if (user.getRole().getName().equals("ORGANIZATION_SUPER_MODERATOR")) {

            OrganizationSuperModerator superModerator = superModeratorRepository.findByUser(user).orElseThrow(() ->
                            new RuntimeException("Пользователь не является супер-модератором организации"));

            return superModerator.getOrganization();
        }


        OrganizationModerator moderator = moderatorRepository.findByUser(user).orElseThrow(() ->
                        new RuntimeException("Пользователь не является модератором организации"));

        if (!moderator.getApproved()) {throw new RuntimeException("Модератор еще не подтвержден");
        }

        return moderator.getOrganization();
    }
}