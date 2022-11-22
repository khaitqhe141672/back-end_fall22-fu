package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.request.PostVoucherRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostVoucherService {

    public ResponseEntity<JwtResponse> getPostVoucherByPostID(Long postID);

    public ResponseEntity<MessageResponse> insertPostVoucher(Long postID, PostVoucherRequest postVoucherRequest);
}
