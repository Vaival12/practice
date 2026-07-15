package com.practiceSystem.dto.response;

public class OrganizationModeratorResponse {

    private Long id;

    private Long organizationId;

    private Long userId;

    private Boolean isSuperModerator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getSuperModerator() {
        return isSuperModerator;
    }

    public void setSuperModerator(Boolean superModerator) {
        isSuperModerator = superModerator;
    }
}