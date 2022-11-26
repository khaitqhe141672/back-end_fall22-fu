package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByPassword(String password);

    Boolean existsByEmail(String email);

    User getUserByCodeActive(String otp);

    User getUserByEmail(String email);

    User findUserById(Long id);

    User getUserByUsername(String username);
}
