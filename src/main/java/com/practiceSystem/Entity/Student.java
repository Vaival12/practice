package com.practiceSystem.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;

    private String lastName;


    private String firstName;


    private String middleName;


    private LocalDate birthDate;


    private String phone;


    private Integer course;


    private String groupName;


    private String specialization;

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public University getUniversity() {
        return university;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getCourse() {
        return course;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}