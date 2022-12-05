package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface DashboardService {

    public ResponseEntity<ResponseObject> dashboardAdmin();

    public ResponseEntity<ResponseObject> dashboardHost();
}
