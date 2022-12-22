package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.LoginDto;
import com.homesharing_backend.data.dto.UserDto;
import com.homesharing_backend.data.entity.*;
import com.homesharing_backend.data.repository.*;
import com.homesharing_backend.exception.AuthException;
import com.homesharing_backend.exception.BadRequestAlertException;
import com.homesharing_backend.exception.ConflictException;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.*;
import com.homesharing_backend.security.jwt.JwtUtils;
import com.homesharing_backend.security.services.UserDetailsImpl;
import com.homesharing_backend.service.AuthService;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.homesharing_backend.service.PaymentService;
import com.homesharing_backend.service.PostVoucherService;
import com.homesharing_backend.util.JavaMail;
import com.homesharing_backend.util.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private JwtUtils jwtUtils;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private FollowHostRepository followHostRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private AWSService awsService;

    @Override
    public ResponseEntity<ResponseObject> register(SignupRequest signUpRequest, HttpServletRequest servletRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new ConflictException("Tên tài khoàn đã tồn tại");
        } else if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ConflictException("Email đã tồn tại");
        } else {
            User user = new User(signUpRequest.getUsername(),
                    signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()));
            UserDetail userDetail = new UserDetail();
            userDetail.setFullName(signUpRequest.getFullName());
            userDetail.setDob(signUpRequest.getDob());
            userDetail.setAddress(signUpRequest.getAddress());
            userDetail.setMobile(signUpRequest.getMobile());
            userDetail.setAvatarUrl("https://home-sharing.s3.ap-southeast-1.amazonaws.com/avatar.png");
            user.setUserDetail(userDetail);
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
                        Admin admin = Admin.builder()
                                .user(user)
                                .build();

                        adminRepository.save(admin);
                        break;
                    case "host":
                        Role hostRole = roleRepository.findByName(ERole.ROLE_HOST)
                                .orElseThrow(() -> new NotFoundException("Teacher role is not found"));
//                            roles.add(new UserRole(user, hostRole));
                        user.setRole(hostRole);
                        Host host = Host.builder()
                                .user(user)
                                .typeAccount(2)
                                .build();

                        hostRepository.save(host);
                        break;
                    default:
                        Role customerRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                                .orElseThrow(() -> new NotFoundException("Student role is not found"));
//                            roles.add(new UserRole(user, customerRole));
                        user.setRole(customerRole);
                        Customer customer = Customer.builder()
                                .user(user)
                                .build();

                        customerRepository.save(customer);
                }
//                });
            }
//            user.setRoles(roles);
            String otp = UUID.randomUUID().toString();
            user.setCodeActive(otp);

            LocalDateTime localDateTime = LocalDateTime.now();
            LocalDate localDate = localDateTime.toLocalDate();


            Date dateStart = Date.valueOf(localDate);

            user.setCreateDate(dateStart);

            User savedUser = userRepository.save(user);

//            List<String> resRoles = new ArrayList<>();
//            roles.forEach(e -> resRoles.add(e.getRole().getName().name()));
            LoginDto userDto = modelMapper.map(user, LoginDto.class);
            userDto.setRole(user.getRole().getName().name());
            userDto.setFullName(signUpRequest.getFullName());
            String baseUrl = ServletUriComponentsBuilder.fromRequestUri(servletRequest)
                    .replacePath(null)
                    .build()
                    .toUriString();

            String toEmail = user.getEmail();
            String subject = "Xác nhận tài khoản!";
            String text = "Chào " + userDetail.getFullName() + ",\n" +
                    "Cảm ơn bạn đã dăng ký tài khoản trên HomeSharing! Trước khi bắt đầu," +
                    " bạn cần xác nhận đó là bạn. Bấm vào đường dẫn bên dưới để xác nhận tài khoản: \n" +
                    "http://localhost:4200/auth/confirm-account?otp=" + otp + "\n" +
                    "\nTrân trọng,\n" +
                    "\nĐội ngũ HomeSharing.";
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

        User user = userRepository.getUserByUsername(signInRequest.getUsername());

        if (Objects.isNull(user)) {
            throw new AuthException("Tai Khoan khong hop le");
        } else {

            if (passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
                Authentication authentication =
                        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(),
                                signInRequest.getPassword()));

                if (user.getStatus() == 2) {
                    throw new AuthException("Tai khoan bi chan");
                } else {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    String jwt = jwtUtils.generateJwtToken(authentication);
                    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

                    List<String> roles = userDetails.getAuthorities().stream()
                            .map(item -> item.getAuthority()).collect(Collectors.toList());

                    LoginDto userDto = LoginDto.builder()
                            .email(userDetails.getEmail())
                            .username(userDetails.getUsername()).role(roles.get(0))
                            .status(user.getStatus()).build();
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

            } else {
                throw new BadRequestAlertException("mat khau current khong khop");
            }
        }
    }

    @Override
    public ResponseEntity<ResponseObject> confirmAccount(String otp) {

        Map data = new HashMap<String, Object>();

        User user = userRepository.getUserByCodeActive(otp);
        if (user != null) {
            user.setStatus(1);
            User updateRole = userRepository.save(user);

            LoginDto dto = LoginDto.builder()
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

        if (user != null) {
            if (role == 1) {
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

    @Override
    public ResponseEntity<ResponseObject> existAccountByUsername(String username) {
        Map data = new HashMap<String, Object>();
        if (userRepository.existsByUsername(username.trim())) {
            data.put("user", username + " đã tồn tại");
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("USERNAME_EXIST", data));
        } else {
            data.put("user", username + " chưa tồn tại");
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("USERNAME_NOT_EXIST", data));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> existAccountByEmail(String email) {
        Map data = new HashMap<String, Object>();
        if (userRepository.existsByEmail(email.trim())) {
            data.put("user", email + " đã tồn tại");
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("EMAIL_EXIST", data));
        } else {
            data.put("user", email + " chưa tồn tại");
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("EMAIL_NOT_EXIST", data));
        }
    }

    @Override
    public ResponseEntity<MessageResponse> logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();


        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(200, "Login success full!"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(400, " Login success fail!"));
        }
    }

    /*
     * typeAccount = 1 tk thuong
     * typeAccount = 2 tk co tich xanh
     */
    @Override
    public ResponseEntity<JwtResponse> profile() {

        User user = userRepository.findUserById(SecurityUtils.getPrincipal().getId());

        if (Objects.isNull(user)) {
            throw new NotFoundException("User khong ton tai");
        } else {
            UserDto dto = UserDto.builder()
                    .userID(user.getId())
                    .username(user.getUsername())
                    .createDate(user.getCreateDate())
                    .dob(user.getUserDetail().getDob())
                    .mobile(user.getUserDetail().getMobile())
                    .fullName(user.getUserDetail().getFullName())
                    .userDetailID(user.getUserDetail().getUserDetailId())
                    .urlImage(user.getUserDetail().getAvatarUrl())
                    .email(user.getEmail())
                    .address(user.getUserDetail().getAddress())
                    .status(user.getStatus())
                    .role(user.getRole().getName().name())
                    .build();


            if (user.getRole().getName().name().equals("ROLE_HOST")) {
                Host host = hostRepository.getHostsByUser_Id(user.getId());
                dto.setTypeAccountHost(host.getTypeAccount());
            }

            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), dto));
        }
    }

    @Override
    public ResponseEntity<MessageResponse> changePassword(ChangePasswordRequest changePasswordRequest) {

        User user = userRepository.findUserById(SecurityUtils.getPrincipal().getId());

        if (Objects.isNull(user)) {
            throw new NotFoundException("User khong ton tai");
        } else {
            if (passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
                userRepository.save(user);
                return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(200, "Change password success full!"));
            } else {
                throw new BadRequestAlertException("mat khau current khong khop");
            }
        }
    }

    @Override
    public ResponseEntity<MessageResponse> forgotPassword(String email, HttpServletRequest servletRequest) {

        User user = userRepository.getUserByEmail(email);

        if (Objects.isNull(user)) {
            throw new NotFoundException("email khong ton tai");
        } else {
            String resetPassword = UUID.randomUUID().toString();
            String baseUrl = ServletUriComponentsBuilder.fromRequestUri(servletRequest)
                    .replacePath(null)
                    .build()
                    .toUriString();
            String toEmail = user.getEmail();
            String subject = "Xác nhận tài khoản!";
            String text = "Chào " + user.getUserDetail().getFullName() + ",\n" +
                    "Bạn cần xác nhận đó là bạn. Bấm vào đường dẫn bên dưới để xác nhận tài khoản: \n" +
                    "http://localhost:4200/auth/forgot-password?otp=" + resetPassword + "&email=" + user.getEmail() + "\n" +
                    "\nTrân trọng,\n" +
                    "\nĐội ngũ HomeSharing.";
            user.setCodeActive(resetPassword);
            userRepository.save(user);
            new JavaMail().sentEmail(toEmail, subject, text);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(200, "Reset-password success check mail"));
        }
    }

    @Override
    public ResponseEntity<MessageResponse> confirmResetPassword(String token) {

        User user = userRepository.getUserByCodeActive(token);

        if (Objects.isNull(user)) {
            throw new NotFoundException("otp khong ton tai");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(200, "confirm-reset success"));
        }
    }

    @Override
    public ResponseEntity<MessageResponse> resetPassword(ForgotPasswordRequest forgotPasswordRequest) {

        User user = userRepository.getUserByEmail(forgotPasswordRequest.getEmail());

        if (Objects.isNull(user)) {
            throw new NotFoundException("email khong ton tai");
        } else {
            user.setPassword(passwordEncoder.encode(forgotPasswordRequest.getPassword()));
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(200, "reset-password success"));
        }
    }

    @Override
    public ResponseEntity<MessageResponse> editAvatar(MultipartFile file) {

        User user = userRepository.findUserById(SecurityUtils.getPrincipal().getId());

        if (Objects.isNull(user)) {
            throw new NotFoundException("user khong ton tai");
        } else {

            UserDetail userDetail = userDetailRepository.getUserDetailByUserDetailId(user.getUserDetail().getUserDetailId());

            List<String> list = List.of(userDetail.getAvatarUrl().split("/"));

            String nameFile = awsService.upload(file);

            String urlImage = "https://home-sharing.s3.ap-southeast-1.amazonaws.com/avatar.png";

//            if (userDetail.getAvatarUrl().equalsIgnoreCase(urlImage)) {
//                userDetail.setAvatarUrl(nameFile);
//            } else {
//                awsService.delete(list.get(3));
            userDetail.setAvatarUrl(nameFile);
//            }
            userDetail.setAvatarUrl(nameFile);
            userDetailRepository.save(userDetail);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(200, "edit avatar thanh cong"));
        }
    }

    @Override
    public ResponseEntity<MessageResponse> editProfile(EditProfileRequest editProfileRequest) {

        User user = userRepository.findUserById(SecurityUtils.getPrincipal().getId());

        user.getUserDetail().setAddress(editProfileRequest.getAddress());
        user.getUserDetail().setMobile(editProfileRequest.getMobile());
        user.getUserDetail().setFullName(editProfileRequest.getFullName());

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(200, "edit profile thanh cong"));
    }
}
