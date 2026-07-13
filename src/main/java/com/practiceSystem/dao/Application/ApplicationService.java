package com.practiceSystem.dao.Application;

import com.practiceSystem.Entity.Application;
import com.practiceSystem.Entity.ApplicationStatus;
import com.practiceSystem.dao.base.BaseService;
import com.practiceSystem.dto.request.ApplicationRequest;

public interface ApplicationService extends BaseService<Application, Long> {
    Application create(ApplicationRequest request);
    Application updateStatus(Long id, ApplicationStatus status);
    Application update(Long id, ApplicationRequest request);


}
