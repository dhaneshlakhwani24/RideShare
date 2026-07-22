package com.dhaneshlakhwani.rideshare.repository;

import com.dhaneshlakhwani.rideshare.entity.User;
import com.dhaneshlakhwani.rideshare.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByMobileNumber(String mobileNumber);
    long countByRole(Role role);
}
