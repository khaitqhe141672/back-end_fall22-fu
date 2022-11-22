package com.homesharing_backend.presentation.controller;

import com.homesharing_backend.presentation.payload.request.ComplaintRequest;
import com.homesharing_backend.presentation.payload.request.ReportRequest;
import com.homesharing_backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/create-reportRate")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> createReportRateByHost(@RequestBody ReportRequest reportRequest,
                                                    @RequestParam("rate-id") Long rateID) {
        return reportService.createReportRate(reportRequest, rateID);
    }

    @PostMapping("/create-reportPost")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> createReportPostByCustomer(@RequestBody ReportRequest reportRequest,
                                                        @RequestParam("post-id") Long postID) {
        return reportService.createReportPost(reportRequest, postID);
    }

    @PostMapping("/create-complaintRate")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> createComplaintRateByHost(@RequestBody ComplaintRequest ComplaintRequest,
                                                       @RequestParam("reportRate-id") Long reportRateID) {
        return reportService.createComplaintRate(ComplaintRequest, reportRateID);
    }

    @PostMapping("/create-complaintPost")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> createComplaintPostByHost(@RequestBody ComplaintRequest ComplaintRequest,
                                                       @RequestParam("reportPost-id") Long reportPostID) {
        return reportService.createComplaintPost(ComplaintRequest, reportPostID);
    }

    @GetMapping("/list-reportPost-host")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> getAllReportPostByHost(@RequestParam("post-id") Long postID) {
        return reportService.getAllReportPostByHost(postID);
    }

    @PutMapping("/resolve-complaintPost")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> resolveComplaintPostByAdmin(@RequestParam("complaintPost-id") Long complaintPostID,
                                                         @RequestParam("type") int type) {
        return reportService.resolveComplaintPost(complaintPostID, type);
    }

    @PutMapping("/resolve-complaintRate")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> resolveComplaintRateByAdmin(@RequestParam("complaintRate-id") Long complaintRateID,
                                                         @RequestParam("type") int type) {
        return reportService.resolveComplaintRate(complaintRateID, type);
    }

    @GetMapping("/list-reportRate-admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllReportRateByAdmin(@RequestParam("index-page") int indexPage) {
        return reportService.getAllReportRateByAdmin(indexPage);
    }

    @GetMapping("/list-reportPost-admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllReportPostByAdmin(@RequestParam("index-page") int indexPage) {
        return reportService.getAllReportPostByPostOfHost(indexPage);
    }

    @GetMapping("/list-reportPost-detail-admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllReportPosDetailByAdmin(@RequestParam("post-id") Long postID,
                                                          @RequestParam("index-page") int indexPage) {
        return reportService.getAllDetailReportPostByPostIDOfHost(postID, indexPage);
    }

    @GetMapping("/all-complaint-post")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllComplaintPostByAdmin(@RequestParam("index-page") int indexPage) {
        return reportService.getAllComplaintPostByAdmin(indexPage);
    }

    @GetMapping("/all-complaint-rate")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllComplaintRateByAdmin(@RequestParam("index-page") int indexPage) {
        return reportService.getAllComplaintRateByAdmin(indexPage);
    }

    @GetMapping("/all-complaint-post-host")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> getAllComplaintPostByHost(@RequestParam("index-page") int indexPage) {
        return reportService.getAllComplaintPostByHost(indexPage);
    }

    @PutMapping("/update-status-report-rate")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateStatusReportRateByAdmin(@RequestParam("report-rate-id") Long reportRateID,
                                                           @RequestParam("status") int status) {
        return reportService.updateStatusReportRate(reportRateID, status);
    }

    @PutMapping("/update-status-report-post")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateStatusReportPostByAdmin(@RequestParam("report-post-id") Long reportPostID,
                                                           @RequestParam("status") int status) {
        return reportService.updateStatusReportPost(reportPostID, status);
    }
}
