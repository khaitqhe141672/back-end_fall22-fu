package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.HistoryHandleReportPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryHandleReportPostRepository extends JpaRepository<HistoryHandleReportPost, Long> {

    Page<HistoryHandleReportPost> getHistoryHandleReportPostByPost_Id(Long postID, PageRequest pageRequest);

    HistoryHandleReportPost getHistoryHandleReportPostByIdAndPost_Id(Long id, Long postID);

}
