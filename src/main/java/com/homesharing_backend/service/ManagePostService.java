package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ManagePostService {

    public ResponseEntity<ResponseObject> getAllPostByAdmin(int indexPage);

    public ResponseEntity<MessageResponse> checkPaymentPostByAdmin();

    public ResponseEntity<ResponseObject> getAllPostByHost(int indexPage);

    public ResponseEntity<ResponseObject> getAllReportPostByHost(int indexPage, Long postID);

    public ResponseEntity<ResponseObject> getAllBookingByHost(int indexPage, int status);

    public ResponseEntity<ResponseObject> getTotalBookingStatusByHost();

    public ResponseEntity<ResponseObject> getCurrentBooking(int indexPage);

    public ResponseEntity<MessageResponse> updateStatusPostByHost(Long postID, int status);

}
