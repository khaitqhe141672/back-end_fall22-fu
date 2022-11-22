package com.homesharing_backend.presentation.controller;

import com.homesharing_backend.service.ReportTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/report-type")
public class ReportTypeController {

    @Autowired
    private ReportTypeService reportTypeService;

    @GetMapping("/all-report-type-customer")
    public ResponseEntity<?> getAllReportTypeOfCustomer() {
        return reportTypeService.getAllReportTypeOfCustomer();
    }

    @GetMapping("/all-report-type-host")
    public ResponseEntity<?> getAllReportTypeOfHost() {
        return reportTypeService.getAllReportTypeOfHost();
    }
}
