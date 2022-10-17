package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ProvinceService {

    public ResponseEntity<JwtResponse> getRecommendedPlaces();
}
