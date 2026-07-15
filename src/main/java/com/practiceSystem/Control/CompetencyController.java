package com.practiceSystem.Control;

import com.practiceSystem.Entity.Competency;
import com.practiceSystem.dao.Competency.CompetencyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/competencies")
public class CompetencyController {

    private final CompetencyService competencyService;

    public CompetencyController(CompetencyService competencyService) {
        this.competencyService = competencyService;
    }

    @GetMapping
    public List<Competency> findAll() {
        return competencyService.findAll();
    }

    @GetMapping("/{id}")
    public Competency findById(@PathVariable Long id) {
        return competencyService.findById(id).orElseThrow();
    }

    @PostMapping
    public Competency create(@RequestBody Competency competency) {
        return competencyService.save(competency);
    }

    @PutMapping("/{id}")
    public Competency update(
            @PathVariable Long id,
            @RequestBody Competency competency) {

        Competency oldCompetency =
                competencyService.findById(id).orElseThrow();

        oldCompetency.setName(competency.getName());

        return competencyService.save(oldCompetency);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        competencyService.deleteById(id);
    }
}