package fpt.edu.backendfall22fu.presentation.controller;

import fpt.edu.backendfall22fu.service.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestAwsController {

    @Autowired
    private Test test;

    @PostMapping(value = "/aws")
    public ResponseEntity<?> demoAws(@RequestPart(value = "file") MultipartFile file){
        return test.updateFile(file);
    }
}
