package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportTypeRepository extends JpaRepository<ReportType, Long> {

    ReportType getReportTypeById(Long id);

    List<ReportType> getReportTypeByStatus(int status);
}
