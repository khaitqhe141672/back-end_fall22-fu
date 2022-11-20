package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ManageRateService {

    public ResponseEntity<ResponseObject> getAllRateByHost(int indexPage);

    public ResponseEntity<ResponseObject> getAllDetailRateByHost(Long postID, int indexPage);
}
