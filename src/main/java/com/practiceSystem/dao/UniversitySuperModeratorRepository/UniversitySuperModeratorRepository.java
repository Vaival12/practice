package com.practiceSystem.dao.UniversitySuperModeratorRepository;

import com.practiceSystem.Entity.UniversitySuperModerator;
import com.practiceSystem.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface  UniversitySuperModeratorRepository extends JpaRepository<UniversitySuperModerator, Long> {

    Optional<UniversitySuperModerator> findByUser(User user);

    Optional<UniversitySuperModerator> findByUniversityId(Long universityId);

}
