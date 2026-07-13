package com.practiceSystem.dao.Vacancy;

import com.practiceSystem.Entity.Vacancy;
import com.practiceSystem.Entity.VacancyStatus;
import com.practiceSystem.dao.base.BaseService;
import com.practiceSystem.dto.request.VacancyRequest;

public interface VacancyService extends BaseService<Vacancy, Long> {

    Vacancy create(VacancyRequest request);

    Vacancy update(Long id, VacancyRequest request);

    Vacancy updateStatus(Long id, VacancyStatus status);
}