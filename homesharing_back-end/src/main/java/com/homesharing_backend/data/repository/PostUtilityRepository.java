package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.PostUtility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostUtilityRepository extends JpaRepository<PostUtility, Long> {

    Optional<PostUtility> getPostUtilitiesByPost_Id(Long postID);
}
