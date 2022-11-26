package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.ERole;
import com.homesharing_backend.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

    Boolean existsByName(ERole name);
}