package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.ReportPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportPostRepository extends JpaRepository<ReportPost, Long> {

    ReportPost getReportPostById(Long id);
}
