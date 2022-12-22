package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.PaymentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface PaymentService {

    public ResponseEntity<ResponseObject> rePayment(Long paymentPackageID);

    public ResponseEntity<ResponseObject> createPayment(PaymentRequest paymentRequest) throws UnsupportedEncodingException;

    public ResponseEntity<JwtResponse> paymentResult(Long postID, Long packagePaymentID);

    public ResponseEntity<MessageResponse> checkTimePostPayment();
}
