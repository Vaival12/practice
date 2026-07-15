package com.practiceSystem.ApplicationServices;


import com.practiceSystem.Entity.Direction;
import com.practiceSystem.dao.Direction.DirectionRepository;
import com.practiceSystem.dao.Direction.DirectionService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public class DirectionServiceImpl
        implements DirectionService {


    private final DirectionRepository repository;



    public DirectionServiceImpl(
            DirectionRepository repository
    ){

        this.repository = repository;

    }



    @Override
    public List<Direction> findAll(){

        return repository.findAll();

    }



    @Override
    public Optional<Direction> findById(Long id){

        return repository.findById(id);

    }



    @Override
    public Direction save(Direction direction){

        return repository.save(direction);

    }



    @Override
    public void deleteById(Long id){

        repository.deleteById(id);

    }

}