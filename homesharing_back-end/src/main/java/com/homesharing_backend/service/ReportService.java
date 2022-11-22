package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
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

    public ResponseEntity<ResponseObject> getAllReportRateByAdmin(int indexPage);

    public ResponseEntity<ResponseObject> getAllReportPostByAdmin(int indexPage);

    public ResponseEntity<JwtResponse> getAllReportPostByHost(Long postID);

    public ResponseEntity<ResponseObject> getAllReportPostByPostOfHost(int indexPage);

    public ResponseEntity<ResponseObject> getAllDetailReportPostByPostIDOfHost(Long postID, int indexPage);

    public ResponseEntity<ResponseObject> getAllComplaintRateByAdmin(int indexPage);

    public ResponseEntity<ResponseObject> getAllComplaintPostByAdmin(int indexPage);

    public ResponseEntity<ResponseObject> getAllComplaintPostByHost(int indexPage);
}
