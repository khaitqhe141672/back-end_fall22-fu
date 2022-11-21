package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.PaymentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {

    public ResponseEntity<ResponseObject> rePayment(Long paymentPackageID);

    public ResponseEntity<ResponseObject> createPayment(PaymentRequest paymentRequest);
}
