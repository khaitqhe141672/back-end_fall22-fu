package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ManageAccountService {

    public ResponseEntity<ResponseObject> viewAccountHost(int indexPage);

    public ResponseEntity<ResponseObject> viewAccountCustomer(int indexPage);

    public ResponseEntity<JwtResponse> getOneAccountHostByAdmin(Long hostID);

    public ResponseEntity<JwtResponse> getOneAccountCustomerByAdmin(Long customerID);
}
