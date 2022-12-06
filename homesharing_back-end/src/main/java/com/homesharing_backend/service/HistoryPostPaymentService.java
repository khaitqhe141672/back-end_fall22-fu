package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface HistoryPostPaymentService {

    public ResponseEntity<ResponseObject> getAllHistoryPostPayment(int index);
}
