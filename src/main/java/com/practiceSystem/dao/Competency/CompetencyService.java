package com.practiceSystem.dao.Competency;


import com.practiceSystem.Entity.Competency;

import java.util.List;
import java.util.Optional;


public interface CompetencyService {


    List<Competency> findAll();


    Optional<Competency> findById(Long id);


    Competency save(Competency competency);


    void deleteById(Long id);

}