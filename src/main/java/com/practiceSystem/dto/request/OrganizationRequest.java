package com.practiceSystem.dto.request;

import java.util.List;

public class OrganizationRequest {

    private String name;

    private String description;

    private String address;

    private String website;

    private String email;

    private String phone;

    private List<Long> competencyIds;

    private List<Long> directionIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}