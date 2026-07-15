package com.practiceSystem.ApplicationServices;


import com.practiceSystem.Entity.Competency;
import com.practiceSystem.dao.Competency.CompetencyRepository;
import com.practiceSystem.dao.Competency.CompetencyService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CompetencyServiceImpl implements CompetencyService {


    private final CompetencyRepository repository;



    public CompetencyServiceImpl(
            CompetencyRepository repository
    ){

        this.repository = repository;

    }



    @Override
    public List<Competency> findAll(){

        return repository.findAll();

    }



    @Override
    public Optional<Competency> findById(Long id){

        return repository.findById(id);

    }



    @Override
    public Competency save(Competency competency){

        return repository.save(competency);

    }



    @Override
    public void deleteById(Long id){

        repository.deleteById(id);

    }

}