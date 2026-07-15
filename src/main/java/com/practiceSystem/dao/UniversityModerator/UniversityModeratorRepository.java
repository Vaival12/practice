package com.practiceSystem.dao.UniversityModerator;

import com.practiceSystem.Entity.OrganizationModerator;
import com.practiceSystem.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.practiceSystem.Entity.UniversityModerator;

import java.util.List;
import java.util.Optional;

public interface UniversityModeratorRepository extends JpaRepository<UniversityModerator, Long> {
    List<UniversityModerator> findByUniversityId(Long universityId);

    Optional<UniversityModerator> findByUniversityIdAndUserId(Long universityId, Long userId);

    Optional<UniversityModerator> findByUser(User user);

}