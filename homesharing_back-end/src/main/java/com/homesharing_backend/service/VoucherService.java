package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.VoucherRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VoucherService {

    public ResponseEntity<ResponseObject> getAllVoucher();

    public ResponseEntity<ResponseObject> getOneVoucher(Long id);

    public ResponseEntity<MessageResponse> insertVoucher(List<VoucherRequest> voucherRequest);

    public ResponseEntity<ResponseObject> updateVoucher(Long id, VoucherRequest voucherRequest);

    public ResponseEntity<ResponseObject> updateStatusVoucher(Long id, int status);

    public ResponseEntity<JwtResponse> getAllVoucherByHostID();
}
