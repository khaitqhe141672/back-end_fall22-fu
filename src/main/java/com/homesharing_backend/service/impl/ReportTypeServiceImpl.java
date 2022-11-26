package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.entity.ReportType;
import com.homesharing_backend.data.repository.ReportTypeRepository;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.service.ReportService;
import com.homesharing_backend.service.ReportTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ReportTypeServiceImpl implements ReportTypeService {

    @Autowired
    private ReportTypeRepository reportTypeRepository;

    @Override
    public ResponseEntity<JwtResponse> getAllReportTypeOfCustomer() {

        List<ReportType> reportTypes = reportTypeRepository.getReportTypeByStatus(1);

        if (Objects.isNull(reportTypes)) {
            throw new NotFoundException("Khong co report-type cua customer");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), reportTypes));
        }
    }

    @Override
    public ResponseEntity<JwtResponse> getAllReportTypeOfHost() {

        List<ReportType> reportTypes = reportTypeRepository.getReportTypeByStatus(2);

        if (Objects.isNull(reportTypes)) {
            throw new NotFoundException("Khong co report-type cua customer");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), reportTypes));
        }
    }
}
