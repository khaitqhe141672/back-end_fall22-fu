package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.VoucherRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface VoucherService {

    public ResponseEntity<ResponseObject> getAllVoucher();

    public ResponseEntity<ResponseObject> getOneVoucher(Long id);

    public ResponseEntity<ResponseObject> insertVoucher(VoucherRequest voucherRequest);

    public ResponseEntity<ResponseObject> updateVoucher(Long id, VoucherRequest voucherRequest);

    public ResponseEntity<ResponseObject> updateStatusVoucher(Long id, int status);
}
