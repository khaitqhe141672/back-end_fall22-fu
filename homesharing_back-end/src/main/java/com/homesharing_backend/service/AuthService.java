package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.LoginRequest;
import com.homesharing_backend.presentation.payload.request.SignupRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface AuthService {

    public ResponseEntity<ResponseObject> register(SignupRequest signUpRequest, HttpServletRequest servletRequest);
    public ResponseEntity<ResponseObject> login(LoginRequest signInRequest);
    public ResponseEntity<ResponseObject> confirmAccount(String otp);
    public ResponseEntity<ResponseObject> updateRole(String email, int role);

    public ResponseEntity<ResponseObject> existAccountByUsername(String username);

    public ResponseEntity<ResponseObject> existAccountByEmail(String email);
}
