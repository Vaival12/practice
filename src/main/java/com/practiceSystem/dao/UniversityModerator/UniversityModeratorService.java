package com.practiceSystem.dao.UniversityModerator;

import com.practiceSystem.Entity.UniversityModerator;
import com.practiceSystem.dao.base.BaseService;
import com.practiceSystem.dto.request.UniversityModeratorRequest;

import java.util.List;
import java.util.Optional;

public interface UniversityModeratorService extends BaseService<UniversityModerator, Long> {

    UniversityModerator create(UniversityModeratorRequest request);

    Optional<UniversityModerator> findById(Long id);

    List<UniversityModerator> findAll();

    List<UniversityModerator> findByUniversityId(Long universityId);

    void deleteById(Long id);
}