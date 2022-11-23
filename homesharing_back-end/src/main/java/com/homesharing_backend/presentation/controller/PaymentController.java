package com.homesharing_backend.presentation.controller;

import com.homesharing_backend.presentation.payload.request.PaymentRequest;
import com.homesharing_backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create-payment")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequest paymentRequest) throws UnsupportedEncodingException {
        return paymentService.createPayment(paymentRequest);
    }

    @GetMapping("/payment-result")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> paymentResult(@RequestParam(value = "post-id") Long postID,
                                           @RequestParam(value = "payment-package-id") Long paymentPackageID) {
        return paymentService.paymentResult(postID, paymentPackageID);
    }
}
