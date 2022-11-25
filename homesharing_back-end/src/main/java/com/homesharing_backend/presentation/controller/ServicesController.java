package com.homesharing_backend.presentation.controller;

import com.homesharing_backend.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/services")
public class ServicesController {

    @Autowired
    private ServicesService servicesService;

    @GetMapping("/all-list")
    public ResponseEntity<?> getAllServiceByHost() {
        return servicesService.getAllServiceByHost();
    }
}
