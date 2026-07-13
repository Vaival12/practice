package com.practiceSystem.dao.User;

import com.practiceSystem.Entity.User;
import com.practiceSystem.dao.base.BaseService;
import com.practiceSystem.dto.request.UserRequest;


import java.util.Optional;

public interface UserService extends BaseService<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    User create(UserRequest request);

    User update(Long id, UserRequest request);

}