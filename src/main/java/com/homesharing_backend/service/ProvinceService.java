package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ProvinceService {

    public ResponseEntity<JwtResponse> getRecommendedPlaces();

    public ResponseEntity<JwtResponse> getAllProvince();

    public ResponseEntity<JwtResponse> getAllDistrict();

    public ResponseEntity<JwtResponse> getAllDistrictByProvinceID(Long provinceID);

    public ResponseEntity<JwtResponse> getOneProvinceByProvinceID(Long provinceID);
}
