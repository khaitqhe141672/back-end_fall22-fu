package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.PostUtility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostUtilityRepository extends JpaRepository<PostUtility, Long> {
}
