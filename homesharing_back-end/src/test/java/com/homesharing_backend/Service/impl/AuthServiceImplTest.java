package com.homesharing_backend.Service.impl;

import com.homesharing_backend.data.entity.*;
import com.homesharing_backend.data.repository.*;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.ChangePasswordRequest;
import com.homesharing_backend.presentation.payload.request.ForgotPasswordRequest;
import com.homesharing_backend.presentation.payload.request.LoginRequest;
import com.homesharing_backend.presentation.payload.request.SignupRequest;
import com.homesharing_backend.security.jwt.JwtUtils;
import com.homesharing_backend.service.impl.AWSService;
import com.homesharing_backend.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

class AuthServiceImplTest {
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    UserRepository userRepository;
    @Mock
    RoleRepository roleRepository;
    @Mock
    JwtUtils jwtUtils;
    @Mock
    ModelMapper modelMapper;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    HostRepository hostRepository;
    @Mock
    AdminRepository adminRepository;
    @Mock
    FollowHostRepository followHostRepository;
    @Mock
    UserDetailRepository userDetailRepository;
    @Mock
    AWSService awsService;
    @InjectMocks
    AuthServiceImpl authServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        when(userRepository.existsByUsername(anyString())).thenReturn(Boolean.TRUE);
        when(userRepository.existsByEmail(anyString())).thenReturn(Boolean.TRUE);
        when(roleRepository.findByName(any())).thenReturn(null);

        ResponseEntity<ResponseObject> result = authServiceImpl.register(new SignupRequest("username", "password", "email", "role", "fullName", "address", "mobile", null), null);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testLogin() {
        when(userRepository.getUserByUsername(anyString())).thenReturn(new User(Long.valueOf(1), "kha1111á1", "fsa1d@gmail.com", "$2a$10$RFMGot8OLIlbVOvBoCbAIOO2kxF/nMAW1gp7x6wJCduEbsaFWjVY2", new Role(Long.valueOf(2), ERole.ROLE_CUSTOMER), new UserDetail(Long.valueOf(1), "fullName", "mobile", "address", "avatarUrl", null), "codeActive", null, 0));
        when(jwtUtils.generateJwtToken(any())).thenReturn("generateJwtTokenResponse");
        when(customerRepository.getByUser_Username(anyString())).thenReturn(new Customer(Long.valueOf(1), new User(Long.valueOf(1), "username", "email", "password", new Role(Long.valueOf(1), ERole.ROLE_HOST), new UserDetail(Long.valueOf(1), "fullName", "mobile", "address", "avatarUrl", null), "codeActive", null, 0)));
        when(hostRepository.getHostsByUser_Username(anyString())).thenReturn(new Host(Long.valueOf(1), new User(Long.valueOf(1), "username", "email", "password", new Role(Long.valueOf(1), ERole.ROLE_HOST), new UserDetail(Long.valueOf(1), "fullName", "mobile", "address", "avatarUrl", null), "codeActive", null, 0), 0));
        when(adminRepository.getAdminByUser_Username(anyString())).thenReturn(new Admin(Long.valueOf(1), new User(Long.valueOf(1), "username", "email", "password", new Role(Long.valueOf(1), ERole.ROLE_HOST), new UserDetail(Long.valueOf(1), "fullName", "mobile", "address", "avatarUrl", null), "codeActive", null, 0)));

        ResponseEntity<ResponseObject> result = authServiceImpl.login(new LoginRequest("kha1111á1", "$2a$10$RFMGot8OLIlbVOvBoCbAIOO2kxF/nMAW1gp7x6wJCduEbsaFWjVY2"));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testConfirmAccount() {
        when(userRepository.getUserByCodeActive(anyString())).thenReturn(new User(Long.valueOf(1), "username", "email", "password", new Role(Long.valueOf(1), ERole.ROLE_HOST), new UserDetail(Long.valueOf(1), "fullName", "mobile", "address", "avatarUrl", null), "codeActive", null, 0));

        ResponseEntity<ResponseObject> result = authServiceImpl.confirmAccount("otp");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testUpdateRole() {
        when(userRepository.getUserByEmail(anyString())).thenReturn(new User(Long.valueOf(1), "username", "email", "password", new Role(Long.valueOf(1), ERole.ROLE_HOST), new UserDetail(Long.valueOf(1), "fullName", "mobile", "address", "avatarUrl", null), "codeActive", null, 0));
        when(roleRepository.findByName(any())).thenReturn(null);

        ResponseEntity<ResponseObject> result = authServiceImpl.updateRole("email", 0);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testExistAccountByUsername() {
        when(userRepository.existsByUsername(anyString())).thenReturn(Boolean.TRUE);

        ResponseEntity<ResponseObject> result = authServiceImpl.existAccountByUsername("username");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testExistAccountByEmail() {
        when(userRepository.existsByEmail(anyString())).thenReturn(Boolean.TRUE);

        ResponseEntity<ResponseObject> result = authServiceImpl.existAccountByEmail("email");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testLogout() {
        ResponseEntity<MessageResponse> result = authServiceImpl.logout(null, null);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testProfile() {
        when(userRepository.findUserById(anyLong())).thenReturn(new User(Long.valueOf(1), "username", "email", "password", new Role(Long.valueOf(1), ERole.ROLE_HOST), new UserDetail(Long.valueOf(1), "fullName", "mobile", "address", "avatarUrl", null), "codeActive", null, 0));
        when(hostRepository.getHostsByUser_Id(anyLong())).thenReturn(new Host(Long.valueOf(1), new User(Long.valueOf(1), "username", "email", "password", new Role(Long.valueOf(1), ERole.ROLE_HOST), new UserDetail(Long.valueOf(1), "fullName", "mobile", "address", "avatarUrl", null), "codeActive", null, 0), 0));

        ResponseEntity<JwtResponse> result = authServiceImpl.profile();
        Assertions.assertEquals(null, result);
    }

    @Test
    void testChangePassword() {
        when(userRepository.findUserById(anyLong())).thenReturn(new User(Long.valueOf(1), "username", "email", "password", new Role(Long.valueOf(1), ERole.ROLE_HOST), new UserDetail(Long.valueOf(1), "fullName", "mobile", "address", "avatarUrl", null), "codeActive", null, 0));

        ResponseEntity<MessageResponse> result = authServiceImpl.changePassword(new ChangePasswordRequest("currentPassword", "newPassword"));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testForgotPassword() {
        when(userRepository.getUserByEmail(anyString())).thenReturn(new User(Long.valueOf(1), "username", "email", "password", new Role(Long.valueOf(1), ERole.ROLE_HOST), new UserDetail(Long.valueOf(1), "fullName", "mobile", "address", "avatarUrl", null), "codeActive", null, 0));

        ResponseEntity<MessageResponse> result = authServiceImpl.forgotPassword("email", null);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testConfirmResetPassword() {
        when(userRepository.getUserByCodeActive(anyString())).thenReturn(new User(Long.valueOf(1), "username", "email", "password", new Role(Long.valueOf(1), ERole.ROLE_HOST), new UserDetail(Long.valueOf(1), "fullName", "mobile", "address", "avatarUrl", null), "codeActive", null, 0));

        ResponseEntity<MessageResponse> result = authServiceImpl.confirmResetPassword("token");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testResetPassword() {
        when(userRepository.getUserByEmail(anyString())).thenReturn(new User(Long.valueOf(1), "username", "email", "password", new Role(Long.valueOf(1), ERole.ROLE_HOST), new UserDetail(Long.valueOf(1), "fullName", "mobile", "address", "avatarUrl", null), "codeActive", null, 0));

        ResponseEntity<MessageResponse> result = authServiceImpl.resetPassword(new ForgotPasswordRequest("email", "password"));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testEditAvatar() {
        when(userRepository.findUserById(anyLong())).thenReturn(new User(Long.valueOf(1), "username", "email", "password", new Role(Long.valueOf(1), ERole.ROLE_HOST), new UserDetail(Long.valueOf(1), "fullName", "mobile", "address", "avatarUrl", null), "codeActive", null, 0));
        when(userDetailRepository.getUserDetailByUserDetailId(anyLong())).thenReturn(new UserDetail(Long.valueOf(1), "fullName", "mobile", "address", "avatarUrl", null));
        when(awsService.upload(any())).thenReturn("uploadResponse");
        when(awsService.delete(anyString())).thenReturn("deleteResponse");

        ResponseEntity<MessageResponse> result = authServiceImpl.editAvatar(null);
        Assertions.assertEquals(null, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme