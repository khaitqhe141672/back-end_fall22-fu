package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.ComplaintPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintPostRepository extends JpaRepository<ComplaintPost, Long> {

    ComplaintPost getComplaintPostById(Long id);
}
