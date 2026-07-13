package com.practiceSystem.ApplicationServices;


import com.practiceSystem.dao.University.UniversityRepository;
import com.practiceSystem.dao.User.UserRepository;
import com.practiceSystem.dto.request.UniversityModeratorRequest;
import org.springframework.stereotype.Service;
import com.practiceSystem.dao.UniversityModerator.UniversityModeratorRepository;
import com.practiceSystem.dao.UniversityModerator.UniversityModeratorService;
import com.practiceSystem.Entity.UniversityModerator;
import com.practiceSystem.Entity.University;
import com.practiceSystem.Entity.User;

import java.util.List;
import java.util.Optional;

@Service
public class UniversityModeratorServiceImpl implements UniversityModeratorService {

    private final UniversityModeratorRepository moderatorRepository;

    private final UniversityRepository universityRepository;

    private final UserRepository userRepository;

    public UniversityModeratorServiceImpl(UniversityModeratorRepository moderatorRepository, UniversityRepository universityRepository, UserRepository userRepository) {

        this.moderatorRepository = moderatorRepository;
        this.universityRepository = universityRepository;
        this.userRepository = userRepository;

    }

    @Override
    public UniversityModerator create(UniversityModeratorRequest request) {

        University university = universityRepository.findById(request.getUniversityId()).orElseThrow();

        User user = userRepository.findById(request.getUserId()).orElseThrow();

        UniversityModerator moderator = new UniversityModerator();

        moderator.setUniversity(university);
        moderator.setUser(user);

        return moderatorRepository.save(moderator);
    }

    @Override
    public List<UniversityModerator> findAll() {

        return moderatorRepository.findAll();

    }

    @Override
    public Optional<UniversityModerator> findById(Long id) {

        return moderatorRepository.findById(id);

    }

    @Override
    public UniversityModerator save(UniversityModerator moderator) {

        return moderatorRepository.save(moderator);

    }

    @Override
    public List<UniversityModerator> findByUniversityId(Long universityId) {

        return moderatorRepository.findByUniversityId(universityId);

    }
    @Override
    public void deleteById(Long id) {

        moderatorRepository.deleteById(id);

    }
}