package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface LikesDislikesService {

    public ResponseEntity<ResponseObject> createLikeOrDislikeRateByCustomer(Long rateID, Long postID, int type);

    public ResponseEntity<ResponseObject> editLikeOrDislikeRateByCustomer(Long likeOrDislikeID, int type);

    public ResponseEntity<JwtResponse> countLikeOrDislikeRateByCustomer(Long rateID);
}
