package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.UserDto;
import com.homesharing_backend.data.entity.*;
import com.homesharing_backend.data.repository.RoleRepository;
import com.homesharing_backend.data.repository.UserRepository;
import com.homesharing_backend.exception.ConflictException;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.LoginRequest;
import com.homesharing_backend.presentation.payload.request.SignupRequest;
import com.homesharing_backend.security.jwt.JwtUtils;
import com.homesharing_backend.security.services.UserDetailsImpl;
import com.homesharing_backend.service.AuthService;

import java.util.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<ResponseObject> register(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new ConflictException("Username is already taken");
        } else if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ConflictException("Email is already taken");
        } else {
            User user = new User(signUpRequest.getUsername(),
                    signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()));
            UserDetail userDetail = new UserDetail();
            userDetail.setFirstName(signUpRequest.getFirstName());
            userDetail.setLastName(signUpRequest.getLastName());
            user.setUserDetail(userDetail);
//            Set<String> strRoles = signUpRequest.getRole();
//            Set<UserRole> roles = new HashSet<>();
            String strRoles = signUpRequest.getRole();
            Role roles = new Role();
            if (strRoles == null || strRoles.isEmpty()) {
                Role customerRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                        .orElseThrow(() -> new NotFoundException("Student role is not found"));
//                roles.add(new UserRole(user, customerRole));
                user.setRole(customerRole);
            } else {
//                strRoles.forEach(role -> {
                    switch (strRoles) {
                        case "admin":
                            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new NotFoundException("Admin role is not found"));
//                            roles.add(new UserRole(user, adminRole));
                            user.setRole(adminRole);
                            break;
                        case "host":
                            Role hostRole = roleRepository.findByName(ERole.ROLE_HOST)
                                    .orElseThrow(() -> new NotFoundException("Teacher role is not found"));
//                            roles.add(new UserRole(user, hostRole));
                            user.setRole(hostRole);
                            break;
                        default:
                            Role customerRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                                    .orElseThrow(() -> new NotFoundException("Student role is not found"));
//                            roles.add(new UserRole(user, customerRole));
                            user.setRole(customerRole);
                    }
//                });
            }
//            user.setRoles(roles);
            User savedUser = userRepository.save(user);
//            List<String> resRoles = new ArrayList<>();
//            roles.forEach(e -> resRoles.add(e.getRole().getName().name()));
            UserDto userDto = modelMapper.map(user, UserDto.class);
            userDto.setRole(user.getRole().getName().name());
            userDto.setFirstName(signUpRequest.getFirstName());
            userDto.setLastName(signUpRequest.getLastName());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("User registered successfully!", new HashMap<>() {
                {
                    put("user", userDto);
                }
            }));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> login(LoginRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        UserDto userDto = UserDto.builder().email(userDetails.getEmail()).username(userDetails.getUsername()).role(roles.get(0)).build();
        Map data = new HashMap<String, Object>();
//        if (roles.contains(ERole.ROLE_CUSTOMER.name())) {
//            Student student = studentRepository.getByUser_Username(userDto.getUsername());
//            data.put("studentId", student.getStudentId());
//            System.out.println(student.getStudentId());
//        }
//        if (roles.contains(ERole.ROLE_TEACHER.name())) {
//            Teacher teacher = teacherRepository.getByUser_Username(userDto.getUsername());
//            data.put("teacherId", teacher.getTeacherId());
//        }
//        if (roles.contains(ERole.ROLE_ADMIN.name())) {
//            Admin admin = adminRepository.getByUser_Username(userDto.getUsername());
//            data.put("adminId", admin.getAdminId());
//        }
        data.put("userID", userDetails.getId());
        data.put("user", userDto);
        data.put("token", "Bearer " + jwt);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Sign in successfully", data));
    }
}
