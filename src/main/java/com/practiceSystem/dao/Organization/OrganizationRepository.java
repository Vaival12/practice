package com.practiceSystem.dao.Organization;

import org.springframework.data.jpa.repository.JpaRepository;
import com.practiceSystem.Entity.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

}