package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ManageVoucherService {

    public ResponseEntity<ResponseObject> getAllVoucherByHost(int indexPage);

    public ResponseEntity<MessageResponse> updateVoucher(int status, Long voucherID);
}
