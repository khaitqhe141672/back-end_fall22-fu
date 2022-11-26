package com.homesharing_backend.presentation.controller;


import com.homesharing_backend.service.PostService;
import com.homesharing_backend.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/home")
public class HomeController {

    @Autowired
    private PostService postService;

    @Autowired
    private ProvinceService provinceService;

    @GetMapping("/interesting-place")
    public ResponseEntity<?> getInterestingPlace(){
        return postService.getInterestingPlaceByPost();
    }

    @GetMapping("/recommended-places")
    public ResponseEntity<?> getRecommendedPlaces(){
        return provinceService.getRecommendedPlaces();
    }

    @GetMapping("/post-top-rate")
    public ResponseEntity<?>  getPostTopRate(){
        return postService.getTopPostByRate();
    }
}
