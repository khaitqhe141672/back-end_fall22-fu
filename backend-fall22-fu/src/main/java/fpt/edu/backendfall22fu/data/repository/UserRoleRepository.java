package fpt.edu.backendfall22fu.data.repository;

import fpt.edu.backendfall22fu.data.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
