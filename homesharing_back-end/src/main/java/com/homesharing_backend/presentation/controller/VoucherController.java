package com.homesharing_backend.presentation.controller;

import com.homesharing_backend.presentation.payload.request.VoucherRequest;
import com.homesharing_backend.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/voucher")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> getAllVoucher() {
        return voucherService.getAllVoucher();
    }

    @GetMapping("/get-one")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> getOneVoucherByID(@RequestParam("id") Long id) {
        return voucherService.getOneVoucher(id);
    }

    @PostMapping("/insert")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> insertVoucher(@Valid @RequestBody VoucherRequest voucherRequest) {
        return voucherService.insertVoucher(voucherRequest);
    }
}
