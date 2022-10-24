package com.homesharing_backend.presentation.controller;

import com.homesharing_backend.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/address")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    @GetMapping("/province")
    public ResponseEntity<?> getAllProvince() {
        return provinceService.getAllProvince();
    }

    @GetMapping("/district")
    public ResponseEntity<?> getAllDistrict() {
        return provinceService.getAllDistrict();
    }

    @GetMapping("/district-by-provinceID")
    public ResponseEntity<?> getAllDistrictByProvinceID(@RequestParam("province-id") Long provinceID) {
        return provinceService.getAllDistrictByProvinceID(provinceID);
    }

    @GetMapping("/getOne-provinceByID")
    public ResponseEntity<?> getOneProvinceByProvinceID(@RequestParam("province-id") Long provinceID) {
        return provinceService.getOneProvinceByProvinceID(provinceID);
    }
}
