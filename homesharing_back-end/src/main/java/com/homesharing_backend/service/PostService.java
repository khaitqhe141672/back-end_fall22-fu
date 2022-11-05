package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.PostRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface PostService {

    public ResponseEntity<JwtResponse> getInterestingPlaceByPost();

    public ResponseEntity<JwtResponse> getTopPostByRate();

    public ResponseEntity<ResponseObject> createPosting(PostRequest postRequest);

    public ResponseEntity<JwtResponse> getAllPostByHost();
}
