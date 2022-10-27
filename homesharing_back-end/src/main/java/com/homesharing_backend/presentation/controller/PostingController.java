package com.homesharing_backend.presentation.controller;

import com.homesharing_backend.presentation.payload.request.PostRequest;
import com.homesharing_backend.service.PostImageService;
import com.homesharing_backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/posting")
public class PostingController {
    @Autowired
    private PostService postService;

    @Autowired
    private PostImageService postImageService;

    @PostMapping(value = "/create-posting")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> createPosting(@RequestBody PostRequest postRequest){
        return postService.createPosting(postRequest);
    }

    @PostMapping(value = "/insert-post-image")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> insertPostImage(@RequestParam("file") List<MultipartFile> multipartFiles,
                                             @RequestParam("post-id") Long postID){
        return postImageService.insertPostImage(multipartFiles, postID);
    }
}
