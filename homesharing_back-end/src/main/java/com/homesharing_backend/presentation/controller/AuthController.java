package com.homesharing_backend.presentation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.homesharing_backend.presentation.payload.request.*;
import com.homesharing_backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest,
                                          HttpServletRequest servletRequest) {
        return authService.register(signUpRequest, servletRequest);
    }

    @GetMapping("/confirm-account")
    public ResponseEntity<?> confirmAccount(@RequestParam("otp") String otp) {
        return authService.confirmAccount(otp);
    }

    @PutMapping("/update-role")
    public ResponseEntity<?> updateRole(@RequestParam("email") String email,
                                        @RequestParam("type") int type) {
        return authService.updateRole(email, type);
    }

    @GetMapping("/exist-username")
    public ResponseEntity<?> existUsername(@RequestParam("username") String username) {
        return authService.existAccountByUsername(username);
    }

    @GetMapping("/exist-email")
    public ResponseEntity<?> existEmail(@RequestParam("email") String email) {
        return authService.existAccountByEmail(email);
    }

    @GetMapping("/logout")
    @PreAuthorize("hasRole('ROLE_HOST') or hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> logout(HttpServletRequest servletRequest, HttpServletResponse httpServletResponse) {
        return authService.logout(servletRequest, httpServletResponse);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('ROLE_HOST') or hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> profile() {
        return authService.profile();
    }

    @PutMapping("/change-password")
    @PreAuthorize("hasRole('ROLE_HOST') or hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return authService.changePassword(changePasswordRequest);
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email,
                                            HttpServletRequest servletRequest) {
        return authService.forgotPassword(email, servletRequest);
    }

    @GetMapping("/confirm-forgot-password")
    public ResponseEntity<?> confirmForgotPassword(@RequestParam("otp") String otp) {
        return authService.confirmResetPassword(otp);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        return authService.resetPassword(forgotPasswordRequest);
    }

    @PutMapping("/edit-avatar")
    @PreAuthorize("hasRole('ROLE_HOST') or hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> editAvatar(@RequestParam("file") MultipartFile file) {
        return authService.editAvatar(file);
    }

    @PutMapping("/edit-profile")
    @PreAuthorize("hasRole('ROLE_HOST') or hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> editAvatar(@RequestBody EditProfileRequest editProfileRequest) {
        return authService.editProfile(editProfileRequest);
    }

}