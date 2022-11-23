package com.homesharing_backend.presentation.controller;

import com.homesharing_backend.service.ManagePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/manage-post")
public class ManagePostController {

    @Autowired
    private ManagePostService managePostService;

    @GetMapping("/view-all-post")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllPostByAdmin(@RequestParam("index-page") int indexPage) {
        return managePostService.getAllPostByAdmin(indexPage);
    }

    @GetMapping("/view-all-post-host")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> getAllPostByHost(@RequestParam("index-page") int indexPage) {
        return managePostService.getAllPostByHost(indexPage);
    }

    @GetMapping("/all-report-post")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> getAllReportPostByPostID(@RequestParam("post-id") Long postID,
                                                      @RequestParam("index-page") int indexPage) {
        return managePostService.getAllReportPostByHost(indexPage, postID);
    }

    @GetMapping("/all-booking-host")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> getStatusBookingByHost(@RequestParam("index-page") int indexPage,
                                                    @RequestParam("status") int status) {
        return managePostService.getAllBookingByHost(indexPage, status);
    }

    @PutMapping("/check-post-payment")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> checkPostPaymentByAdmin() {
        return managePostService.checkPaymentPostByAdmin();
    }

    @GetMapping("/total-status")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> getTotalStatusBookingByHost() {
        return managePostService.getTotalBookingStatusByHost();
    }

    @GetMapping("/all-current-booking-host")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> getAllCurrentBookingByHost(@RequestParam("index-page") int indexPage) {
        return managePostService.getCurrentBooking(indexPage);
    }

    @PutMapping("/update-status-post-host")
    public ResponseEntity<?> updateStatusPostByHost(@RequestParam("post-id") Long postID,
                                                    @RequestParam("status") int status) {
        return managePostService.updateStatusPostByHost(postID, status);
    }
}
