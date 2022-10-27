package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.request.RateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface RateService {

    public ResponseEntity<JwtResponse> getAllRate(Long postID);

    public ResponseEntity<MessageResponse> createRateByCustomer(RateRequest rateRequest, Long bookingDetailID);

    public ResponseEntity<MessageResponse> editRateByCustomer(RateRequest rateRequest, Long rateID);

    public ResponseEntity<MessageResponse> deleteRateByCustomer(Long rateID);

}
