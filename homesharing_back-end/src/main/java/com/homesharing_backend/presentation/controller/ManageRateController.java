package com.homesharing_backend.presentation.controller;

import com.homesharing_backend.service.ManageRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/manage-rate")
public class ManageRateController {

    @Autowired
    private ManageRateService manageRateService;

    @GetMapping("/all-rate")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> getAllRateByHost(@RequestParam("index-page") int indexPage) {
        return manageRateService.getAllRateByHost(indexPage);
    }

    @GetMapping("/all-detail-rate")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> getAllDetailRate(@RequestParam("post-id") Long postID,
                                              @RequestParam("index-page") int indexPage) {
        return manageRateService.getAllDetailRateByHost(postID, indexPage);
    }
}
