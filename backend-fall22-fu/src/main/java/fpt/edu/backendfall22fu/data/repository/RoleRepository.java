package fpt.edu.backendfall22fu.data.repository;

import fpt.edu.backendfall22fu.data.entity.ERole;
import fpt.edu.backendfall22fu.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

    Boolean existsByName(ERole name);
}
