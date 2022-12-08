package com.homesharing_backend.presentation.controller;


import com.homesharing_backend.service.PostService;
import com.homesharing_backend.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/home")
public class HomeController {

    @Autowired
    private PostService postService;

    @Autowired
    private ProvinceService provinceService;

    @GetMapping("/interesting-place")
    public ResponseEntity<?> getInterestingPlace() {
        return postService.getInterestingPlaceByPost();
    }

    @GetMapping("/recommended-places")
    public ResponseEntity<?> getRecommendedPlaces() {
        return provinceService.getRecommendedPlaces();
    }

    @GetMapping("/post-top-rate")
    public ResponseEntity<?> getPostTopRate() {
        return postService.getTopPostByRate();
    }

    @GetMapping("/post-by-province")
    public ResponseEntity<?> getPostByProvince(@RequestParam("provinceID") Long provinceID,
                                               @RequestParam("index-page") int indexPage) {
        return postService.getPostByProvinceID(provinceID, indexPage);
    }

}
