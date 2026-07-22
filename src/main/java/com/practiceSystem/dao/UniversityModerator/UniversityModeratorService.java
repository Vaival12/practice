package com.practiceSystem.dao.UniversityModerator;

import com.practiceSystem.Entity.UniversityModerator;
import com.practiceSystem.dao.base.BaseService;
import com.practiceSystem.dto.request.UniversityModeratorRequest;

import java.util.List;

public interface UniversityModeratorService extends BaseService<UniversityModerator, Long> {

    UniversityModerator create(UniversityModeratorRequest request);

    List<UniversityModerator> findByUniversityId(Long universityId);

    UniversityModerator approve(Long id);

    List<UniversityModerator> getPendingModerators();

    UniversityModerator update(Long id, UniversityModeratorRequest request);
}