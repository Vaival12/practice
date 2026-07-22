package com.practiceSystem.dao.OrganizationSuperModerator;

import com.practiceSystem.Entity.OrganizationSuperModerator;
import com.practiceSystem.Entity.UniversitySuperModerator;
import com.practiceSystem.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationSuperModeratorRepository extends JpaRepository<OrganizationSuperModerator, Long> {

    Optional<OrganizationSuperModerator> findByUser(User user);

    Optional<OrganizationSuperModerator> findByOrganizationId(Long organizationId);
}
