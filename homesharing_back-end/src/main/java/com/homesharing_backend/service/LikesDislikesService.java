package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface LikesDislikesService {

    public ResponseEntity<MessageResponse> createLikeOrDislikeRateByCustomer(Long rateID, int type);

    public ResponseEntity<MessageResponse> editLikeOrDislikeRateByCustomer(Long likeOrDislikeID, int type);

    public ResponseEntity<JwtResponse> countLikeOrDislikeRateByCustomer(Long rateID);
}
