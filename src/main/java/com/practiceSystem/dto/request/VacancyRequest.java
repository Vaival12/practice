package com.practiceSystem.dto.request;

import com.practiceSystem.Entity.VacancyStatus;
import jakarta.persistence.Column;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class VacancyRequest {

    private Long organizationId;

    private String title;

    private String description;

    private String requirements;

    private String direction;

    private String practiceResult;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDate practiceStart;

    private LocalDate practiceEnd;

    private Long statusId;

    private List<Long> competencyIds;

    private List<Long> directionIds;

    private Integer maxStudents;

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
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

    public String getPracticeResult() {
        return practiceResult;
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
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

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public List<Long> getCompetencyIds() {
        return competencyIds;
    }

    public void setCompetencyIds(List<Long> competencyIds) {
        this.competencyIds = competencyIds;
    }

    public List<Long> getDirectionIds() {
        return directionIds;
    }

    public void setDirectionIds(List<Long> directionIds) {
        this.directionIds = directionIds;
    }

    public Integer getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(Integer maxStudents) {
        this.maxStudents = maxStudents;
    }
}