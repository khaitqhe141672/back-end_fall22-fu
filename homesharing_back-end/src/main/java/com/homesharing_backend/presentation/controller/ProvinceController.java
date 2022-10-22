package com.homesharing_backend.presentation.controller;

import com.homesharing_backend.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/address")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    @GetMapping("/province")
    public ResponseEntity<?> getAllProvince(){
        return provinceService.getAllProvince();
    }

    @GetMapping("/district")
    public ResponseEntity<?> getAllDistrict(){
        return provinceService.getAllDistrict();
    }
}
