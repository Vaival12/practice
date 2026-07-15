package com.practiceSystem.dao.OrganizationModerator;

import com.practiceSystem.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.practiceSystem.Entity.OrganizationModerator;

import java.util.List;
import java.util.Optional;

public interface OrganizationModeratorRepository extends JpaRepository<OrganizationModerator, Long> {

    List<OrganizationModerator> findByOrganizationId(Long organizationId);

    Optional<OrganizationModerator> findByOrganizationIdAndUserId(Long organizationId, Long userId);

    Optional<OrganizationModerator> findByUser(User user);
}