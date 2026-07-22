package com.practiceSystem.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VacancyResponse {

    private Long id;

    private String title;

    private String description;

    private String requirements;

    private String direction;

    private String practiceResult;

    private String organizationName;

    private String status;

    private LocalDateTime createdAt;

    private LocalDate practiceStart;

    private LocalDate practiceEnd;

    private Integer maxStudents;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getPracticeResult() {
        return practiceResult;
    }

    public void setPracticeResult(String practiceResult) {
        this.practiceResult = practiceResult;
    }

    public LocalDate getPracticeStart() {
        return practiceStart;
    }

    public void setPracticeStart(LocalDate practiceStart) {
        this.practiceStart = practiceStart;
    }

    public LocalDate getPracticeEnd() {
        return practiceEnd;
    }

    public void setPracticeEnd(LocalDate practiceEnd) {
        this.practiceEnd = practiceEnd;
    }

    public Integer getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(Integer maxStudents) {
        this.maxStudents = maxStudents;
    }
}