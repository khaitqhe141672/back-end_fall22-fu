package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.LoginRequest;
import com.homesharing_backend.presentation.payload.request.SignupRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    public ResponseEntity<ResponseObject> register(SignupRequest signUpRequest);
    public ResponseEntity<ResponseObject> login(LoginRequest signInRequest);
}
