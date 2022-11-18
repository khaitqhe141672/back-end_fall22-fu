package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.ReportPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportPostRepository extends JpaRepository<ReportPost, Long> {

    ReportPost getReportPostById(Long id);

    List<ReportPost> getReportPostByPost_Id(Long postID);

    Page<ReportPost> findReportPostByPost_Id(Long postID, PageRequest pageRequest);

    int countReportPostByPost_Id(Long postID);
}
