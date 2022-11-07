package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface FollowAndFavouriteService {

    public ResponseEntity<ResponseObject> followHostByCustomer(Long hostID);

    public ResponseEntity<ResponseObject> editFollowHostByCustomer(Long followHostID);

    public ResponseEntity<JwtResponse> getCountFollowHostByHostID(Long hostID);

    public ResponseEntity<ResponseObject> createFavouritePostByCustomer(Long postID);

    public ResponseEntity<ResponseObject> editFavouritePostByCustomer(Long favouritePostID);

    public ResponseEntity<JwtResponse> getCountFavouritePostByPostID(Long postID);
}
