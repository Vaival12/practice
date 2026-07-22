package com.practiceSystem.Entity;

import jakarta.persistence.*;
import com.practiceSystem.Entity.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    private String lastName;

    @Column(nullable = false)
    private String firstName;

    private String middleName;

    private String phone;

    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime lastLogin;

    @OneToOne(mappedBy = "user")
    private Student student;

    @OneToOne(mappedBy = "user")
    private UniversityModerator universityModerator;

    @OneToOne(mappedBy = "user")
    private UniversitySuperModerator universitySuperModerator;

    @OneToOne(mappedBy = "user")
    private OrganizationModerator organizationModerator;

    @OneToOne(mappedBy = "user")
    private OrganizationSuperModerator organizationSuperModerator;

    public User() {
    }

    public User(String email, String passwordHash, Role role, Boolean active, LocalDate birthDate, String firstName, String middleName, String lastName, String phone) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.active = active;
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phone = phone;
    }

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public Boolean getActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public Student getStudent() {
        return student;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public UniversityModerator getUniversityModerator() {
        return universityModerator;
    }

    public void setUniversityModerator(UniversityModerator universityModerator) {
        this.universityModerator = universityModerator;
    }

    public UniversitySuperModerator getUniversitySuperModerator() {
        return universitySuperModerator;
    }

    public void setUniversitySuperModerator(UniversitySuperModerator universitySuperModerator) {
        this.universitySuperModerator = universitySuperModerator;
    }

    public OrganizationModerator getOrganizationModerator() {
        return organizationModerator;
    }

    public void setOrganizationModerator(OrganizationModerator organizationModerator) {
        this.organizationModerator = organizationModerator;
    }

    public OrganizationSuperModerator getOrganizationSuperModerator() {
        return organizationSuperModerator;
    }

    public void setOrganizationSuperModerator(OrganizationSuperModerator organizationSuperModerator) {
        this.organizationSuperModerator = organizationSuperModerator;
    }
}