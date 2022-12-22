package com.homesharing_backend.presentation.controller;

import com.homesharing_backend.service.PostDetailService;
import com.homesharing_backend.service.PostVoucherService;
import com.homesharing_backend.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/post-detail")
public class PostDetailController {

    @Autowired
    private PostDetailService postDetailService;

    @Autowired
    private RateService rateService;

    @Autowired
    private PostVoucherService postVoucherService;

    @GetMapping("")
    public ResponseEntity<?> getOnePostByID(@RequestParam("post_id") Long postID) {
        return postDetailService.getPostDetailByPostID(postID);
    }

    @GetMapping("/rate-post")
    public ResponseEntity<?> getAllRateByPostID(@RequestParam("post_id") Long postID,
                                                @RequestParam("index-page") int indexPage) {
        return rateService.getAllRate(postID, indexPage);
    }

    @GetMapping("/get-all-voucher")
    public ResponseEntity<?> getAllVoucherPostByPostID(@RequestParam("post_id") Long postID) {
        return postVoucherService.getPostVoucherByPostID(postID);
    }
}
