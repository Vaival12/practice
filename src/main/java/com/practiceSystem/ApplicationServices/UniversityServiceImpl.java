package com.practiceSystem.ApplicationServices;

import com.practiceSystem.Entity.*;
import com.practiceSystem.dao.Permission.PermissionService;
import com.practiceSystem.dao.Role.RoleRepository;
import com.practiceSystem.dao.University.UniversityRepository;
import com.practiceSystem.dao.University.UniversityService;
import com.practiceSystem.dao.UniversityModerator.UniversityModeratorRepository;
import com.practiceSystem.dao.UniversitySuperModeratorRepository.UniversitySuperModeratorRepository;
import com.practiceSystem.dao.User.UserRepository;
import com.practiceSystem.dto.request.UniversityRequest;
import com.practiceSystem.security.AccessService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UniversityServiceImpl implements UniversityService {


    private final UniversityRepository universityRepository;

    private final AccessService accessService;
    private final UserRepository userRepository;
    private final UniversityModeratorRepository moderatorRepository;
    private final RoleRepository roleRepository;
    private final UniversitySuperModeratorRepository superModeratorRepository;
    private final PasswordEncoder passwordEncoder;
    private final PermissionService permissionService;


    public UniversityServiceImpl(UniversityRepository universityRepository, AccessService accessService, UserRepository userRepository, UniversityModeratorRepository moderatorRepository, RoleRepository roleRepository, UniversitySuperModeratorRepository superModeratorRepository, PasswordEncoder passwordEncoder, PermissionService permissionService) {

        this.universityRepository = universityRepository;
        this.accessService = accessService;

        this.userRepository = userRepository;
        this.moderatorRepository = moderatorRepository;
        this.roleRepository = roleRepository;
        this.superModeratorRepository = superModeratorRepository;
        this.passwordEncoder = passwordEncoder;
        this.permissionService = permissionService;
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

        University university = universityRepository.findById(id).orElseThrow();

        if(!accessService.canEditUniversity(university)){
            throw new RuntimeException("Нет доступа");
        }

        universityRepository.delete(university);

    }



    @Override
    public University create(UniversityRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email).orElseThrow();

        University university = new University();

        university.setName(request.getName());
        university.setDescription(request.getDescription());
        university.setAddress(request.getAddress());
        university.setWebsite(request.getWebsite());
        university.setEmail(request.getEmail());
        university.setPhone(request.getPhone());

        University savedUniversity = universityRepository.save(university);

        Role role = roleRepository.findByName("UNIVERSITY_SUPER_MODERATOR").orElseThrow(() -> new RuntimeException("Роль не найдена"));

        currentUser.setRole(role);

        userRepository.save(currentUser);

        // Создаем запись о супермодераторе
        UniversitySuperModerator superModerator = new UniversitySuperModerator();

        superModerator.setUser(currentUser);
        superModerator.setUniversity(savedUniversity);

        superModeratorRepository.save(superModerator);

        return savedUniversity;
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

    @Override
    public University getCurrentUniversity() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow();

        if (user.getRole().getName().equals("UNIVERSITY_SUPER_MODERATOR")) {
            UniversitySuperModerator superModerator = superModeratorRepository.findByUser(user).orElseThrow(() ->
                    new RuntimeException("Пользователь не является супер-модератором университета"));
            return superModerator.getUniversity();
        }

        UniversityModerator moderator = moderatorRepository.findByUser(user).orElseThrow(() ->
                        new RuntimeException("Пользователь не является модератором университета")   );

        return moderator.getUniversity();
    }

    @Override
    public University updateCurrentUniversity(UniversityRequest request) {

        University university = getCurrentUniversity();

        university.setName(request.getName());
        university.setDescription(request.getDescription());
        university.setAddress(request.getAddress());
        university.setWebsite(request.getWebsite());
        university.setEmail(request.getEmail());
        university.setPhone(request.getPhone());

        return universityRepository.save(university);
    }

}