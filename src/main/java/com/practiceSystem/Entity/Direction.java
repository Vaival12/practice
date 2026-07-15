package com.practiceSystem.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "directions")
public class Direction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String name;



    @ManyToMany(mappedBy = "directions")
    private List<Student> students;



    @ManyToMany(mappedBy = "directions")
    private List<Organization> organizations;



    @ManyToMany(mappedBy = "directions")
    private List<Vacancy> vacancies;



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