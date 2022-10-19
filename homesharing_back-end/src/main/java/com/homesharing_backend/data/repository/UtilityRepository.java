package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.Utility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilityRepository extends JpaRepository<Utility, Long> {

    Utility getAllByName(String name);

    Optional<Utility> findUtilityById(Long id);
}
