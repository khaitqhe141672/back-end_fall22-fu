package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<MessageResponse> forgotPassword(String email, HttpServletRequest servletRequest);
    public ResponseEntity<MessageResponse> confirmResetPassword(String token);
    public ResponseEntity<MessageResponse> resetPassword(ForgotPasswordRequest forgotPasswordRequest);
    public ResponseEntity<MessageResponse> editAvatar(MultipartFile file);

    public ResponseEntity<MessageResponse> editProfile(EditProfileRequest editProfileRequest);
}
