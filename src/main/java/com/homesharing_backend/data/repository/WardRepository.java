package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.Wards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WardRepository extends JpaRepository<Wards, Long> {
}
