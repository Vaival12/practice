package com.practiceSystem.dto.request;

public class UniversityModeratorRequest {

    private Long universityId;

    private Long userId;


    public Long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Long universityId) {
        this.universityId = universityId;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}