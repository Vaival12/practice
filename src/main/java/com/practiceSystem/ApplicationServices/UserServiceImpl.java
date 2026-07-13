package com.practiceSystem.ApplicationServices;

import org.springframework.stereotype.Service;
import com.practiceSystem.dao.User.UserRepository;
import com.practiceSystem.dao.User.UserService;
import com.practiceSystem.dto.request.UserRequest;
import com.practiceSystem.Entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
    public User create(UserRequest request){

        User user = new User();

        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setActive(true);

        return userRepository.save(user);

    }


    @Override
    public User update(Long id, UserRequest request) {

        User oldUser = userRepository.findById(id).orElseThrow();

        oldUser.setEmail(request.getEmail());

        oldUser.setRole(request.getRole());

        return userRepository.save(oldUser);

    }

}