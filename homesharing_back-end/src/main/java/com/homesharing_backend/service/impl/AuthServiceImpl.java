package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.UserDto;
import com.homesharing_backend.data.entity.*;
import com.homesharing_backend.data.repository.*;
import com.homesharing_backend.exception.ConflictException;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.LoginRequest;
import com.homesharing_backend.presentation.payload.request.SignupRequest;
import com.homesharing_backend.security.jwt.JwtUtils;
import com.homesharing_backend.security.services.UserDetailsImpl;
import com.homesharing_backend.service.AuthService;

import java.util.*;

import com.homesharing_backend.util.JavaMail;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public ResponseEntity<ResponseObject> register(SignupRequest signUpRequest, HttpServletRequest servletRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new ConflictException("Username is already taken");
        } else if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ConflictException("Email is already taken");
        } else {
            User user = new User(signUpRequest.getUsername(),
                    signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()));
            UserDetail userDetail = new UserDetail();
            userDetail.setFullName(signUpRequest.getFullName());
            userDetail.setDob(signUpRequest.getDob());
            userDetail.setAddress(signUpRequest.getAddress());
            userDetail.setMobile(signUpRequest.getMobile());
            user.setUserDetail(userDetail);
            Role waitRole = roleRepository.findByName(ERole.ROLE_WAIT)
                    .orElseThrow(() -> new NotFoundException("WAIT role is not found"));
            user.setRole(waitRole);

            String otp = UUID.randomUUID().toString();

            user.setCodeActive(otp);
            User savedUser = userRepository.save(user);
            UserDto userDto = modelMapper.map(user, UserDto.class);
            userDto.setRole(user.getRole().getName().name());
            userDto.setFullName(signUpRequest.getFullName());
            String baseUrl = ServletUriComponentsBuilder.fromRequestUri(servletRequest)
                    .replacePath(null)
                    .build()
                    .toUriString();
            String toEmail = user.getEmail();
            String subject = "[JavaMail] - Demo sent email";
            String text = baseUrl + "/api/auth/confirm-account?token=" + otp;
            new JavaMail().sentEmail(toEmail, subject, text);
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
        if (roles.contains(ERole.ROLE_CUSTOMER.name())) {
            Customer customer = customerRepository.getByUser_Username(userDto.getUsername());
            data.put("customerID", customer.getId());
            System.out.println(customer.getId());
        }
        if (roles.contains(ERole.ROLE_HOST.name())) {
            Host teacher = hostRepository.getHostsByUser_Username(userDto.getUsername());
            data.put("hostID", teacher.getId());
        }
        if (roles.contains(ERole.ROLE_ADMIN.name())) {
            Admin admin = adminRepository.getAdminByUser_Username(userDto.getUsername());
            data.put("adminId", admin.getId());
        }
        data.put("userID", userDetails.getId());
        data.put("user", userDto);
        data.put("token", "Bearer " + jwt);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Sign in successfully", data));
    }

    @Override
    public ResponseEntity<ResponseObject> confirmAccount(String otp) {

        Map data = new HashMap<String, Object>();

        User user = userRepository.getUserByCodeActive(otp);
        if (user != null) {
            user.setStatus(1);
            User updateRole = userRepository.save(user);

            UserDto dto = UserDto.builder()
                    .email(user.getEmail())
                    .role(updateRole.getRole().getName().name())
                    .username(user.getUsername())
                    .status(updateRole.getStatus())
                    .build();

            data.put("response", 200);
            data.put("user", dto);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Confirm success", data));
        } else {
            data.put("response", 400);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Confirm fail", data));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> updateRole(String email, int role) {

        User user = userRepository.getUserByEmail(email);
        Map data = new HashMap<String, Object>();

        if(user != null){
            if(role == 1){
                Role customerRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                        .orElseThrow(() -> new NotFoundException("Customer role is not found"));
                user.setRole(customerRole);

                Customer customer = Customer.builder()
                        .user(user)
                        .build();

                customerRepository.save(customer);
            } else {
                Role hostRole = roleRepository.findByName(ERole.ROLE_HOST)
                        .orElseThrow(() -> new NotFoundException("Host role is not found"));
                user.setRole(hostRole);

                Host host = Host.builder()
                        .user(user)
                        .build();

                hostRepository.save(host);
            }
            userRepository.save(user);
            data.put("user", user);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Confirm success", data));
        } else {
            data.put("user", "Not found user");
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Confirm fail", data));
        }
    }


}
