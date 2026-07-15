package com.practiceSystem.ApplicationServices;

import com.practiceSystem.Entity.University;
import com.practiceSystem.dao.University.UniversityRepository;
import com.practiceSystem.dao.University.UniversityService;
import com.practiceSystem.dto.request.UniversityRequest;
import com.practiceSystem.security.AccessService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UniversityServiceImpl implements UniversityService {


    private final UniversityRepository universityRepository;

    private final AccessService accessService;

    public UniversityServiceImpl(UniversityRepository universityRepository, AccessService accessService) {

        this.universityRepository = universityRepository;
        this.accessService = accessService;

    }



    @Override
    public List<University> findAll() {

        return universityRepository.findAll();

    }



    @Override
    public Optional<University> findById(Long id) {

        return universityRepository.findById(id);

    }



    @Override
    public University save(University university) {

        return universityRepository.save(university);

    }



    @Override
    public void deleteById(Long id) {

        University university =
                universityRepository.findById(id)
                        .orElseThrow();


        if(!accessService.canEditUniversity(university)){
            throw new RuntimeException("Нет доступа");
        }


        universityRepository.delete(university);

    }



    @Override
    public University create(UniversityRequest request) {

        University university = new University();

        university.setName(request.getName());
        university.setDescription(request.getDescription());
        university.setAddress(request.getAddress());
        university.setWebsite(request.getWebsite());
        university.setEmail(request.getEmail());
        university.setPhone(request.getPhone());


        return universityRepository.save(university);

    }



    @Override
    public University update(Long id, UniversityRequest request) {

        University university = universityRepository.findById(id).orElseThrow();

        if(!accessService.canEditUniversity(university)){
            throw new RuntimeException("Нет доступа");
        }

        university.setName(request.getName());
        university.setDescription(request.getDescription());
        university.setAddress(request.getAddress());
        university.setWebsite(request.getWebsite());
        university.setEmail(request.getEmail());
        university.setPhone(request.getPhone());

        return universityRepository.save(university);

    }
}