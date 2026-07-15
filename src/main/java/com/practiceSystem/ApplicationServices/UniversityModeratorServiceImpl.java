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
import com.practiceSystem.dao.Permission.PermissionService;

import java.util.List;
import java.util.Optional;

@Service
public class UniversityModeratorServiceImpl implements UniversityModeratorService {

    private final UniversityModeratorRepository moderatorRepository;

    private final UniversityRepository universityRepository;

    private final UserRepository userRepository;

    private final PermissionService permissionService;

    public UniversityModeratorServiceImpl(UniversityModeratorRepository moderatorRepository, UniversityRepository universityRepository, UserRepository userRepository, PermissionService permissionService) {

        this.moderatorRepository = moderatorRepository;
        this.universityRepository = universityRepository;
        this.userRepository = userRepository;
        this.permissionService = permissionService;

    }

    @Override
    public UniversityModerator create(UniversityModeratorRequest request) {

        permissionService.checkUniversitySuperModerator();

        University university = universityRepository.findById(request.getUniversityId()).orElseThrow();

        User user = userRepository.findById(request.getUserId()).orElseThrow();


        UniversityModerator moderator = new UniversityModerator();

        moderator.setUniversity(university);
        moderator.setUser(user);

        return moderatorRepository.save(moderator);
    }

    @Override
    public List<UniversityModerator> findAll() {

        permissionService.checkUniversityModerator();

        return moderatorRepository.findAll();

    }

    @Override
    public Optional<UniversityModerator> findById(Long id) {

        permissionService.checkUniversityModerator();

        return moderatorRepository.findById(id);

    }

    @Override
    public UniversityModerator save(UniversityModerator moderator) {

        permissionService.checkUniversitySuperModerator();

        return moderatorRepository.save(moderator);

    }

    @Override
    public List<UniversityModerator> findByUniversityId(Long universityId) {

        return moderatorRepository.findByUniversityId(universityId);

    }
    @Override
    public void deleteById(Long id) {

        permissionService.checkUniversitySuperModerator();

        moderatorRepository.deleteById(id);

    }
}