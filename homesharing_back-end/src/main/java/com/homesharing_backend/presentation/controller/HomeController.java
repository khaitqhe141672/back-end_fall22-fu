package com.homesharing_backend.presentation.controller;

import com.homesharing_backend.data.repository.PostRepository;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.service.PostService;
import com.homesharing_backend.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/home")
public class HomeController {

    @Autowired
    private PostService postService;

    @Autowired
    private ProvinceService provinceService;

    @GetMapping("/interesting-place")
    public ResponseEntity<?> interestingPlace(){
        return postService.getInterestingPlaceByPost();
    }

    @GetMapping("/recommended-places")
    public ResponseEntity<?> recommendedPlaces(){
        return provinceService.getRecommendedPlaces();
    }
}
