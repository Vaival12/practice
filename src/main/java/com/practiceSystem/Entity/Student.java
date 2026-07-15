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

    @ManyToMany
    @JoinTable(name = "student_direction", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "direction_id"))
    private List<Direction> directions;

    @Column(name = "practice_start")
    private LocalDate practiceStart;

    @Column(name = "practice_end")
    private LocalDate practiceEnd;

    private Integer course;

    private String groupName;

    private String specialization;

    @ManyToMany
    @JoinTable(name = "student_competency", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "competency_id"))
    private List<Competency> competencies;

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public University getUniversity() {
        return university;
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

    public void setCourse(Integer course) {
        this.course = course;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
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

    public List<Competency> getCompetencies() {
        return competencies;
    }

    public void setCompetencies(List<Competency> competencies) {
        this.competencies = competencies;

    }

    public List<Direction> getDirections() {
        return directions;
    }

    public void setDirections(List<Direction> directions) {
        this.directions = directions;
    }
}