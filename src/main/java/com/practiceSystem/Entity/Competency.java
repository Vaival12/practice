package com.practiceSystem.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "competencies")
public class Competency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String name;


    @ManyToMany(mappedBy = "competencies")
    private List<Student> students;


    @ManyToMany(mappedBy = "competencies")
    private List<Organization> organizations;


    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


}