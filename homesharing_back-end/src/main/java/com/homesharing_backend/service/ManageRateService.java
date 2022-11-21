package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.ReportRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ManageRateService {

    public ResponseEntity<ResponseObject> getAllRateByHost(int indexPage);

    public ResponseEntity<ResponseObject> getAllDetailRateByHost(Long postID, int indexPage);

    public ResponseEntity<MessageResponse> createReportRateByHost(Long rateID, ReportRequest reportRequest);
}
