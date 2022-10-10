package com.homesharing_backend.presentation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.homesharing_backend.presentation.payload.request.LoginRequest;
import com.homesharing_backend.presentation.payload.request.SignupRequest;
import com.homesharing_backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                                          HttpServletRequest  servletRequest) {
        return authService.register(signUpRequest, servletRequest);
    }

    @GetMapping("/confirm-account")
    public ResponseEntity<?> confirmAccount(@RequestParam("token") String otp) {
        return authService.confirmAccount(otp);
    }

    @PutMapping("/update-role")
    public ResponseEntity<?> updateRole(@RequestParam("email") String email,
                                        @RequestParam("type") int type) {
        return authService.updateRole(email, type);
    }

    @GetMapping("/exist-username")
    public ResponseEntity<?> existUsername(@RequestParam("username") String username){
        return authService.existAccountByUsername(username);
    }

    @GetMapping("/exist-email")
    public ResponseEntity<?> existEmail(@RequestParam("email") String email){
        return authService.existAccountByEmail(email);
    }
}