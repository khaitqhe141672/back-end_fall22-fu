package com.homesharing_backend.presentation.controller;

import com.homesharing_backend.service.ManageVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/manage-voucher")
public class ManageVoucherController {

    @Autowired
    private ManageVoucherService manageVoucherService;

    @GetMapping("/view-all-voucher")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> getAllVoucherByHost(@RequestParam("index-page") int indexPage) {
        return manageVoucherService.getAllVoucherByHost(indexPage);
    }
}
