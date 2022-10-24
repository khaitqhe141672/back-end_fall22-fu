package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.ChangePasswordRequest;
import com.homesharing_backend.presentation.payload.request.LoginRequest;
import com.homesharing_backend.presentation.payload.request.SignupRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public interface AuthService {
    public ResponseEntity<ResponseObject> register(SignupRequest signUpRequest, HttpServletRequest servletRequest);
    public ResponseEntity<ResponseObject> login(LoginRequest signInRequest);
    public ResponseEntity<ResponseObject> confirmAccount(String otp);
    public ResponseEntity<ResponseObject> updateRole(String email, int role);
    public ResponseEntity<ResponseObject> existAccountByUsername(String username);
    public ResponseEntity<ResponseObject> existAccountByEmail(String email);
    public ResponseEntity<MessageResponse> logout(HttpServletRequest request, HttpServletResponse response);
    public ResponseEntity<JwtResponse> profile();
    public ResponseEntity<MessageResponse> changePassword(ChangePasswordRequest changePasswordRequest);
    
}
