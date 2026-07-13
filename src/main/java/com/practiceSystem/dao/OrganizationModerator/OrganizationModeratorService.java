package com.practiceSystem.dao.OrganizationModerator;

import com.practiceSystem.Entity.OrganizationModerator;
import com.practiceSystem.dao.base.BaseService;
import com.practiceSystem.dto.request.OrganizationModeratorRequest;

import java.util.List;
import java.util.Optional;

public interface OrganizationModeratorService extends BaseService<OrganizationModerator, Long> {
    OrganizationModerator create(OrganizationModeratorRequest request);

    Optional<OrganizationModerator> findById(Long id);

    List<OrganizationModerator> findAll();

    List<OrganizationModerator> findByOrganizationId(Long organizationId);

    void deleteById(Long id);
}