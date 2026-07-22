package com.practiceSystem.ApplicationServices;

import com.practiceSystem.Entity.Organization;
import com.practiceSystem.Entity.OrganizationModerator;
import com.practiceSystem.Entity.OrganizationSuperModerator;
import com.practiceSystem.Entity.User;
import com.practiceSystem.dao.Organization.OrganizationRepository;
import com.practiceSystem.dao.OrganizationModerator.OrganizationModeratorRepository;
import com.practiceSystem.dao.OrganizationModerator.OrganizationModeratorService;
import com.practiceSystem.dao.OrganizationSuperModerator.OrganizationSuperModeratorRepository;
import com.practiceSystem.dao.User.UserRepository;
import com.practiceSystem.dto.request.OrganizationModeratorRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationModeratorServiceImpl
        implements OrganizationModeratorService {

    private final OrganizationModeratorRepository moderatorRepository;

    private final OrganizationRepository organizationRepository;

    private final UserRepository userRepository;

    private final OrganizationSuperModeratorRepository superModeratorRepository;


    public OrganizationModeratorServiceImpl(
            OrganizationModeratorRepository moderatorRepository,
            OrganizationRepository organizationRepository,
            UserRepository userRepository,
            OrganizationSuperModeratorRepository superModeratorRepository
    ) {

        this.moderatorRepository = moderatorRepository;

        this.organizationRepository = organizationRepository;

        this.userRepository = userRepository;

        this.superModeratorRepository = superModeratorRepository;
    }


    @Override
    public OrganizationModerator create(
            OrganizationModeratorRequest request
    ) {

        checkOrganizationSuperModerator();


        Organization organization =
                organizationRepository
                        .findById(request.getOrganizationId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Организация не найдена"
                                )
                        );


        User user =
                userRepository
                        .findById(request.getUserId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Пользователь не найден"
                                )
                        );


        OrganizationModerator moderator =
                new OrganizationModerator();


        moderator.setOrganization(organization);

        moderator.setUser(user);


        return moderatorRepository.save(moderator);
    }


    @Override
    public List<OrganizationModerator> findAll() {

        checkOrganizationModerator();


        return moderatorRepository.findAll();
    }


    @Override
    public Optional<OrganizationModerator> findById(
            Long id
    ) {

        checkOrganizationModerator();


        return moderatorRepository.findById(id);
    }


    @Override
    public List<OrganizationModerator> findByOrganizationId(
            Long organizationId
    ) {

        checkOrganizationModerator();


        return moderatorRepository
                .findByOrganizationId(organizationId);
    }


    @Override
    public OrganizationModerator save(
            OrganizationModerator moderator
    ) {

        checkOrganizationSuperModerator();


        return moderatorRepository.save(moderator);
    }


    @Override
    public void deleteById(
            Long id
    ) {

        checkOrganizationSuperModerator();


        moderatorRepository.deleteById(id);
    }


    @Override
    public OrganizationModerator update(Long id, OrganizationModeratorRequest request) {

        checkOrganizationSuperModerator();


        OrganizationModerator moderator =
                moderatorRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Модератор не найден"
                                )
                        );


        Organization organization =
                organizationRepository
                        .findById(request.getOrganizationId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Организация не найдена"
                                )
                        );


        User user =
                userRepository
                        .findById(request.getUserId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Пользователь не найден"
                                )
                        );


        moderator.setOrganization(organization);

        moderator.setUser(user);


        return moderatorRepository.save(moderator);
    }


    @Override
    public List<OrganizationModerator> getPending() {

        checkOrganizationSuperModerator();


        Organization organization =
                getCurrentSuperOrganization();


        return moderatorRepository
                .findByOrganizationAndApprovedFalse(
                        organization
                );
    }


    @Override
    public OrganizationModerator approve(
            Long id
    ) {

        checkOrganizationSuperModerator();


        OrganizationModerator moderator =
                moderatorRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Модератор не найден"
                                )
                        );


        Organization currentOrganization =
                getCurrentSuperOrganization();


        if (
                !moderator
                        .getOrganization()
                        .getId()
                        .equals(
                                currentOrganization.getId()
                        )
        ) {

            throw new RuntimeException(
                    "Нет доступа к этому модератору"
            );
        }


        moderator.setApproved(true);


        return moderatorRepository.save(moderator);
    }


    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();


        return userRepository
                .findByEmail(
                        authentication.getName()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Текущий пользователь не найден"
                        )
                );
    }


    private Organization getCurrentSuperOrganization() {

        User user =
                getCurrentUser();


        OrganizationSuperModerator superModerator =
                superModeratorRepository
                        .findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Супермодератор организации не найден"
                                )
                        );


        return superModerator.getOrganization();
    }


    private void checkOrganizationSuperModerator() {

        User user =
                getCurrentUser();


        String role =
                user
                        .getRole()
                        .getName();


        if (
                !role.equals("ADMIN")
                        &&
                        !role.equals(
                                "ORGANIZATION_SUPER_MODERATOR"
                        )
        ) {

            throw new RuntimeException(
                    "Недостаточно прав"
            );
        }
    }


    private void checkOrganizationModerator() {

        User user =
                getCurrentUser();


        String role =
                user
                        .getRole()
                        .getName();


        if (
                !role.equals("ADMIN")
                        &&
                        !role.equals(
                                "ORGANIZATION_MODERATOR"
                        )
                        &&
                        !role.equals(
                                "ORGANIZATION_SUPER_MODERATOR"
                        )
        ) {

            throw new RuntimeException(
                    "Недостаточно прав"
            );
        }
    }
}