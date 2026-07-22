package com.practiceSystem.dao.Control;

import com.practiceSystem.Entity.Direction;
import com.practiceSystem.dao.Direction.DirectionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/directions")
public class DirectionController {

    private final DirectionService directionService;

    public DirectionController(DirectionService directionService) {
        this.directionService = directionService;
    }


    @GetMapping
    public List<Direction> findAll() {
        return directionService.findAll();
    }


    @GetMapping("/{id}")
    public Direction findById(@PathVariable Long id) {

        return directionService.findById(id)
                .orElseThrow(() -> new RuntimeException("Направление не найдено"));
    }


    @PostMapping
    public Direction create(@RequestBody Direction direction) {

        return directionService.save(direction);
    }


    @PutMapping("/{id}")
    public Direction update(
            @PathVariable Long id,
            @RequestBody Direction direction) {


        Direction oldDirection =
                directionService.findById(id)
                        .orElseThrow(() -> new RuntimeException("Направление не найдено"));


        oldDirection.setName(direction.getName());


        return directionService.save(oldDirection);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        directionService.deleteById(id);

    }
}