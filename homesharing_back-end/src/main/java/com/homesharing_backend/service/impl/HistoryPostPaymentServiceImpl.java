package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.repository.HistoryPostPaymentRepository;
import com.homesharing_backend.data.repository.HostRepository;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.service.HistoryPostPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HistoryPostPaymentServiceImpl implements HistoryPostPaymentService {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private HistoryPostPaymentRepository historyPostPaymentRepository;

    @Override
    public ResponseEntity<ResponseObject> getAllHistoryPostPayment(int index) {

        int size = 10;
        int page = index - 1;


        return null;
    }
}
