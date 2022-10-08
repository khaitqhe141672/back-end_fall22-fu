package com.homesharing_backend.presentation.controller;

import com.homesharing_backend.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/utility")
public class UtilityController {

    @Autowired
    private UtilityService utilityService;

    @GetMapping("")
    public ResponseEntity<?> getAllUtility(){
        return utilityService.getAllUtility();
    }

    @PostMapping("/insert-utility")
    public ResponseEntity<?> insertUtility(@RequestPart(value = "name") String name){
        return utilityService.insertUtility(name);
    }
}
