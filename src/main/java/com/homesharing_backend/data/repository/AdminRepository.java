package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin getAdminByUser_Username(String useName);
}
