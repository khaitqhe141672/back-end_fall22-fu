package com.homesharing_backend.presentation.controller;

import com.homesharing_backend.presentation.payload.request.RateRequest;
import com.homesharing_backend.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/rate")
public class RateController {

    @Autowired
    private RateService rateService;

    @PostMapping("/create-rate")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> createRateByCustomer(@RequestBody RateRequest rateRequest,
                                                  @RequestParam("bookingDetail-id") Long bookingDetailID) {
        return rateService.createRateByCustomer(rateRequest, bookingDetailID);
    }

    @PutMapping("/edit-rate")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> editRateByCustomer(@RequestBody RateRequest rateRequest,
                                                @RequestParam("rate-id") Long rateID) {
        return rateService.editRateByCustomer(rateRequest, rateID);
    }

    @PutMapping("/delete-rate")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> deleteRateByCustomer(@RequestParam("rate-id") Long rateID) {
        return rateService.deleteRateByCustomer(rateID);
    }
}
