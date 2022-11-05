package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface FollowAndFavouriteService {

    public ResponseEntity<MessageResponse> followHostByCustomer(Long hostID);

    public ResponseEntity<MessageResponse> editFollowHostByCustomer(Long followHostID);

    public ResponseEntity<JwtResponse> getCountFollowHostByHostID(Long hostID);

    public ResponseEntity<MessageResponse> createFavouritePostByCustomer(Long postID);

    public ResponseEntity<MessageResponse> editFavouritePostByCustomer(Long favouritePostID);

    public ResponseEntity<JwtResponse> getCountFavouritePostByPostID(Long postID);
}
