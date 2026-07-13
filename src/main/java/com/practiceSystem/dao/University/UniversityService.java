package com.practiceSystem.dao.University;

import com.practiceSystem.Entity.University;
import com.practiceSystem.Entity.UniversityModerator;
import com.practiceSystem.dao.base.BaseService;
import com.practiceSystem.dto.request.UniversityRequest;

import java.util.List;

public interface UniversityService extends BaseService<University, Long> {

    University create(UniversityRequest request);

    University update(Long id, UniversityRequest request);
}