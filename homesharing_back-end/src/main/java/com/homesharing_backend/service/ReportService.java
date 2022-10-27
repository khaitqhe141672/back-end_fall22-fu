package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.request.ComplaintRequest;
import com.homesharing_backend.presentation.payload.request.ReportRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ReportService {

    public ResponseEntity<MessageResponse> createReportRate(ReportRequest reportRateRequest, Long rateID);

    public ResponseEntity<MessageResponse> createReportPost(ReportRequest reportRateRequest, Long postID);

    public ResponseEntity<MessageResponse> createComplaintRate(ComplaintRequest complaintRequest, Long reportPostID);

    public ResponseEntity<MessageResponse> createComplaintPost(ComplaintRequest complaintRequest, Long reportRateID);

    public ResponseEntity<MessageResponse> resolveComplaintPost(Long complaintPostID, int type);

    public ResponseEntity<MessageResponse> resolveComplaintRate(Long complaintRateID, int type);

    public ResponseEntity<JwtResponse> getAllReportRateByAdmin();

    public ResponseEntity<JwtResponse> getAllReportPostByAdmin();

    public ResponseEntity<JwtResponse> getAllReportRateByHost();

    public ResponseEntity<JwtResponse> getAllReportPostByHost();
}
