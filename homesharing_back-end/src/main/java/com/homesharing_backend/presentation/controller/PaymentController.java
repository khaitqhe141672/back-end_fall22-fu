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
    public ResponseEntity<?> paymentResult(@RequestParam(value = "vnp_ResponseCode") String responseCode,
                                           @RequestParam(value = "vnp_TransactionStatus", required = false) String status,
                                           @RequestParam(value = "vnp_PayDate", required = false) String payDate,
                                           @RequestParam(value = "vnp_OrderInfo", required = false) String orderInfo,
                                           @RequestParam(value = "vnp_TxnRef", required = false) String id) {
        return paymentService.paymentResult(responseCode, status, payDate, orderInfo, id);
    }
}
