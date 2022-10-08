package com.homesharing_backend.presentation.controller;

import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.PaymentPackageRequest;
import com.homesharing_backend.service.PaymentPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/payment-package")
public class PaymentPackageController {

    @Autowired
    private PaymentPackageService paymentPackageService;

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOST')")
    public ResponseEntity<ResponseObject> getAllPaymentPackage() {
        return paymentPackageService.getAllPaymentPackage();
    }

    @PostMapping("/insert")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> insertPaymentPackage(@Valid @RequestBody PaymentPackageRequest paymentPackageRequest) {
        return paymentPackageService.insertPaymentPackage(paymentPackageRequest);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updatePaymentPackage(@RequestParam("id") Long id,
                                                  @Valid @RequestBody PaymentPackageRequest paymentPackageRequest) {
        return paymentPackageService.updatePaymentPackage(id, paymentPackageRequest);
    }

    @GetMapping("/get-one")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getOnePaymentPackageByID(@RequestParam("id") Long id) {
        return paymentPackageService.getOnePaymentPackage(id);
    }

    @PutMapping("/update-status")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateStatusPaymentPackage(@RequestParam("id") Long id,
                                                        @RequestParam("status") int status) {
        return paymentPackageService.updateStatus(id, status);
    }
}
