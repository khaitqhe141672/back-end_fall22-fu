package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.PaymentPackageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface PaymentPackageService {

    public ResponseEntity<ResponseObject> getAllPaymentPackage();

    public ResponseEntity<ResponseObject> insertPaymentPackage(PaymentPackageRequest paymentPackageRequest);

    public ResponseEntity<ResponseObject> updatePaymentPackage(Long id, PaymentPackageRequest paymentPackageRequest);

    public ResponseEntity<ResponseObject> getOnePaymentPackage(Long id);

    public ResponseEntity<ResponseObject> updateStatus(Long id, int status);
}
