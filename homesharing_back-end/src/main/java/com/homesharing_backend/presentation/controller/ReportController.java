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
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> createReportRateByCustomer(@RequestBody ReportRequest reportRequest,
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

//    @GetMapping("/list-reportPost-detail-admin")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<?> getAllReportPosDetailByAdmin(@RequestParam("index-page") int indexPage) {
//        return reportService.getAllReportPostByAdmin(indexPage);
//    }
}
