package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UtilityService {
    public ResponseEntity<ResponseObject> getAllUtility();
    public ResponseEntity<ResponseObject> insertUtility(String name);

    public ResponseEntity<JwtResponse> getAllUtilityByHostID();
}
