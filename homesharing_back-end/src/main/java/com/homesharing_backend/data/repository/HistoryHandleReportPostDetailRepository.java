package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.HistoryHandleReportPostDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryHandleReportPostDetailRepository extends JpaRepository<HistoryHandleReportPostDetail, Long> {

    Page<HistoryHandleReportPostDetail> getHistoryHandleReportPostDetailByHistoryHandleReportPost_Id(Long historyHandleReportPostID, PageRequest pageRequest);

    int countHistoryHandleReportPostDetailByHistoryHandleReportPost_Id(Long historyID);
}
