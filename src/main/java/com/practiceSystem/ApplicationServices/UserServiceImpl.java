package com.practiceSystem.ApplicationServices;

import com.practiceSystem.Entity.*;
import com.practiceSystem.dao.Organization.OrganizationRepository;
import com.practiceSystem.dao.OrganizationModerator.OrganizationModeratorRepository;
import com.practiceSystem.dao.Role.RoleRepository;
import com.practiceSystem.dao.University.UniversityRepository;
import com.practiceSystem.dao.UniversityModerator.UniversityModeratorRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.practiceSystem.dao.User.UserRepository;
import com.practiceSystem.dao.User.UserService;
import com.practiceSystem.dto.request.UserRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    private final UniversityRepository universityRepository;
    private final UniversityModeratorRepository universityModeratorRepository;

    private final OrganizationRepository organizationRepository;
    private final OrganizationModeratorRepository organizationModeratorRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, UniversityRepository universityRepository, UniversityModeratorRepository universityModeratorRepository, OrganizationRepository organizationRepository, OrganizationModeratorRepository organizationModeratorRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.universityRepository = universityRepository;
        this.universityModeratorRepository = universityModeratorRepository;
        this.organizationRepository = organizationRepository;
        this.organizationModeratorRepository = organizationModeratorRepository;
    }


    @Override
    public List<User> findAll() {

        return userRepository.findAll();

    }


    @Override
    public Optional<User> findById(Long id) {

        return userRepository.findById(id);

    }


    @Override
    public User save(User user) {

        return userRepository.save(user);

    }


    @Override
    public void deleteById(Long id) {

        userRepository.deleteById(id);

    }


    @Override
    public Optional<User> findByEmail(String email) {

        return userRepository.findByEmail(email);

    }


    @Override
    public boolean existsByEmail(String email) {

        return userRepository.existsByEmail(email);

    }


    @Override
    public User create(UserRequest request) {

        User user = new User();

        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setMiddleName(request.getMiddleName());
        user.setBirthDate(request.getBirthDate());
        user.setPhone(request.getPhone());

        Role role = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Роль USER не найдена"));

        user.setRole(role);

        user.setRole(role);
        user.setActive(true);

        User savedUser = userRepository.save(user);

        if (role.getName().equals("ORGANIZATION_MODERATOR")) {

            Organization organization = organizationRepository.findById(request.getOrganizationId()).orElseThrow(() ->
                            new RuntimeException("Организация не найдена"));

            OrganizationModerator moderator = new OrganizationModerator();

            moderator.setUser(savedUser);
            moderator.setOrganization(organization);
            moderator.setApproved(false);

            organizationModeratorRepository.save(moderator);
        }

        if (role.getName().equals("UNIVERSITY_MODERATOR")) {

            University university = universityRepository.findById(request.getUniversityId()).orElseThrow(() ->
                    new RuntimeException("Университет не найден"));


            UniversityModerator moderator = new UniversityModerator();

            moderator.setUser(savedUser);
            moderator.setUniversity(university);
            moderator.setApproved(false);

            universityModeratorRepository.save(moderator);
        }


        return savedUser;
    }


    @Override
    public User update(Long id, UserRequest request) {


        User oldUser = userRepository.findById(id).orElseThrow();

        oldUser.setEmail(request.getEmail());

        Role role = roleRepository.findByName(request.getRole()).orElseThrow(() -> new RuntimeException("Роль не найдена"));

        oldUser.setRole(role);

        return userRepository.save(oldUser);

    }

    @Override
    public User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow();
    }

    @Override
    public User updateCurrentUser(UserRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setMiddleName(request.getMiddleName());
        user.setBirthDate(request.getBirthDate());
        user.setPhone(request.getPhone());

        user.setEmail(request.getEmail());

        return userRepository.save(user);
    }

}