package com.practiceSystem.dao.Direction;


import com.practiceSystem.Entity.Direction;

import java.util.List;
import java.util.Optional;


public interface DirectionService {


    List<Direction> findAll();


    Optional<Direction> findById(Long id);


    Direction save(Direction direction);


    void deleteById(Long id);

}