package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.HistoryHandleReportPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryHandleReportPostRepository extends JpaRepository<HistoryHandleReportPost, Long> {
}
