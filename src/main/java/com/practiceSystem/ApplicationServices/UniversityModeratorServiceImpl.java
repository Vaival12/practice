package com.practiceSystem.ApplicationServices;

import com.practiceSystem.Entity.*;
import com.practiceSystem.dao.Permission.PermissionService;
import com.practiceSystem.dao.Student.StudentRepository;
import com.practiceSystem.dao.University.UniversityRepository;
import com.practiceSystem.dao.UniversitySuperModeratorRepository.UniversitySuperModeratorRepository;
import com.practiceSystem.dao.User.UserRepository;
import com.practiceSystem.dto.request.UniversityModeratorRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.practiceSystem.dao.UniversityModerator.UniversityModeratorRepository;
import com.practiceSystem.dao.UniversityModerator.UniversityModeratorService;

import java.util.List;
import java.util.Optional;

@Service
public class UniversityModeratorServiceImpl implements UniversityModeratorService {

    private final UniversityModeratorRepository moderatorRepository;
    private final StudentRepository studentRepository;
    private final UniversityRepository universityRepository;
    private final UserRepository userRepository;
    private final PermissionService permissionService;
    private final UniversitySuperModeratorRepository superModeratorRepository;

    public UniversityModeratorServiceImpl(
            UniversityModeratorRepository moderatorRepository, StudentRepository studentRepository,
            UniversityRepository universityRepository,
            UserRepository userRepository, PermissionService permissionService,
            UniversitySuperModeratorRepository superModeratorRepository
    ) {
        this.moderatorRepository = moderatorRepository;
        this.studentRepository = studentRepository;
        this.universityRepository = universityRepository;
        this.userRepository = userRepository;
        this.permissionService = permissionService;
        this.superModeratorRepository = superModeratorRepository;
    }

    @Override
    public UniversityModerator create(UniversityModeratorRequest request) {

        checkUniversitySuperModerator();

        University university =
                universityRepository
                        .findById(request.getUniversityId())
                        .orElseThrow();

        User user =
                userRepository
                        .findById(request.getUserId())
                        .orElseThrow();

        UniversityModerator moderator = new UniversityModerator();

        moderator.setUniversity(university);
        moderator.setUser(user);

        return moderatorRepository.save(moderator);
    }

    @Override
    public List<UniversityModerator> findAll() {

        checkUniversityModerator();

        return moderatorRepository.findAll();
    }

    @Override
    public Optional<UniversityModerator> findById(Long id) {

        checkUniversityModerator();

        return moderatorRepository.findById(id);
    }

    @Override
    public UniversityModerator save(UniversityModerator moderator) {

        checkUniversitySuperModerator();

        return moderatorRepository.save(moderator);
    }

    @Override
    public List<UniversityModerator> findByUniversityId(Long universityId) {

        return moderatorRepository.findByUniversityId(universityId);
    }

    @Override
    public UniversityModerator update(Long id, UniversityModeratorRequest request) {

        checkUniversitySuperModerator();

        UniversityModerator moderator =
                moderatorRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Модератор не найден"));

        University university =
                universityRepository.findById(request.getUniversityId())
                        .orElseThrow(() ->
                                new RuntimeException("Университет не найден"));

        User user =
                userRepository.findById(request.getUserId())
                        .orElseThrow(() ->
                                new RuntimeException("Пользователь не найден"));


        moderator.setUniversity(university);
        moderator.setUser(user);

        return moderatorRepository.save(moderator);
    }

    @Override
    public void deleteById(Long id) {

        checkUniversitySuperModerator();

        moderatorRepository.deleteById(id);
    }

    @Override
    public UniversityModerator approve(Long id) {

        checkUniversitySuperModerator();

        UniversityModerator moderator =
                moderatorRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Модератор не найден")
                        );

        University currentUniversity = getCurrentSuperUniversity();

        if (!moderator.getUniversity().getId()
                .equals(currentUniversity.getId())) {

            throw new RuntimeException(
                    "Нет доступа к этому университету"
            );
        }

        moderator.setApproved(true);

        return moderatorRepository.save(moderator);
    }

    @Override
    public List<UniversityModerator> getPendingModerators() {

        checkUniversitySuperModerator();

        University university = getCurrentSuperUniversity();

        return moderatorRepository
                .findByUniversityAndApprovedFalse(university);
    }

    private University getCurrentSuperUniversity() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        User user =
                userRepository
                        .findByEmail(authentication.getName())
                        .orElseThrow();

        UniversitySuperModerator superModerator =
                superModeratorRepository
                        .findByUser(user)
                        .orElseThrow();

        return superModerator.getUniversity();
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        return userRepository
                .findByEmail(authentication.getName())
                .orElseThrow();
    }

    private void checkUniversitySuperModerator() {

        User user = getCurrentUser();

        String role = user.getRole().getName();

        if (!role.equals("ADMIN") &&
                !role.equals("UNIVERSITY_SUPER_MODERATOR")) {

            throw new RuntimeException("Недостаточно прав");
        }
    }

    private void checkUniversityModerator() {

        User user = getCurrentUser();

        String role = user.getRole().getName();

        if (!role.equals("ADMIN") &&
                !role.equals("UNIVERSITY_MODERATOR") &&
                !role.equals("UNIVERSITY_SUPER_MODERATOR")) {

            throw new RuntimeException("Недостаточно прав");
        }
    }
}