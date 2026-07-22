package com.practiceSystem.dao.Organization;

import com.practiceSystem.Entity.Organization;
import com.practiceSystem.dao.base.BaseService;
import com.practiceSystem.dto.request.OrganizationRequest;

public interface OrganizationService extends BaseService<Organization, Long> {

    Organization create(OrganizationRequest request);

    Organization update(Long id, OrganizationRequest request);

    Organization getCurrentOrganization();

    Organization updateCurrentOrganization(OrganizationRequest request);

}