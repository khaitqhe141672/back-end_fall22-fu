package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.ReportRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRateRepository extends JpaRepository<ReportRate, Long> {

    ReportRate getReportRateById(Long id);

    int countReportRateByRate_BookingDetail_Post_Id(Long postID);

}
