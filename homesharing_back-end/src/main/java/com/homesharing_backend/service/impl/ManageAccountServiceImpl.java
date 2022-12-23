package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.HostDto;
import com.homesharing_backend.data.dto.UserDto;
import com.homesharing_backend.data.entity.Customer;
import com.homesharing_backend.data.entity.Host;
import com.homesharing_backend.data.entity.User;
import com.homesharing_backend.data.repository.CustomerRepository;
import com.homesharing_backend.data.repository.FollowHostRepository;
import com.homesharing_backend.data.repository.HostRepository;
import com.homesharing_backend.data.repository.UserRepository;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.exception.UpdateDataException;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.service.ManageAccountService;
import com.homesharing_backend.service.PaymentService;
import com.homesharing_backend.service.PostVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class ManageAccountServiceImpl implements ManageAccountService {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private FollowHostRepository followHostRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostVoucherService postVoucherService;

    @Autowired
    private PaymentService paymentService;

    @Override
    public ResponseEntity<ResponseObject> viewAccountHost(int indexPage) {

        paymentService.checkTimePostPayment();
        postVoucherService.checkTimePostVoucher();

        if (Objects.isNull(indexPage)) {
            throw new NotFoundException("Index page null");
        } else {
            int size = 10;
            int page = indexPage - 1;
            Page<Host> hosts = hostRepository.findAll(PageRequest.of(page, size));

            if (Objects.isNull(hosts)) {
                throw new NotFoundException("not data");
            } else {
                List<HostDto> hostDtoList = new ArrayList<>();

                hosts.forEach(h -> {

                    HostDto dto = HostDto.builder()
                            .hostID(h.getId())
                            .userID(h.getUser().getId())
                            .userDetailID(h.getUser().getUserDetail().getUserDetailId())
                            .username(h.getUser().getUsername())
                            .email(h.getUser().getEmail())
                            .urlImage(h.getUser().getUserDetail().getAvatarUrl())
                            .roleName(h.getUser().getRole().getName().name())
                            .createDate(h.getUser().getCreateDate())
                            .dob(h.getUser().getUserDetail().getDob())
                            .fullName(h.getUser().getUserDetail().getFullName())
                            .mobile(h.getUser().getUserDetail().getMobile())
                            .address(h.getUser().getUserDetail().getAddress())
                            .totalFollower(followHostRepository.totalFollowHostByCustomer(h.getId()))
                            .status(h.getUser().getStatus())
                            .build();

                    hostDtoList.add(dto);
                });
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
                    {
                        put("HostList", hostDtoList);
                        put("SizePage", hosts.getTotalPages());
                    }
                }));
            }
        }
    }

    @Override
    public ResponseEntity<ResponseObject> viewAccountCustomer(int indexPage) {

        paymentService.checkTimePostPayment();
        postVoucherService.checkTimePostVoucher();

        if (Objects.isNull(indexPage)) {
            throw new NotFoundException("Index page null");
        } else {
            int size = 10;
            int page = indexPage - 1;

            Page<Customer> customers = customerRepository.findAll(PageRequest.of(page, size));

            if (Objects.isNull(customers)) {
                throw new NotFoundException("not data");
            } else {
                List<UserDto> dtoList = new ArrayList<>();

                customers.forEach(c -> {
                    UserDto dto = UserDto.builder()
                            .customerID(c.getId())
                            .userID(c.getUser().getId())
                            .email(c.getUser().getEmail())
                            .username(c.getUser().getUsername())
                            .userDetailID(c.getUser().getUserDetail().getUserDetailId())
                            .urlImage(c.getUser().getUserDetail().getAvatarUrl())
                            .createDate(c.getUser().getCreateDate())
                            .dob(c.getUser().getUserDetail().getDob())
                            .fullName(c.getUser().getUserDetail().getFullName())
                            .mobile(c.getUser().getUserDetail().getMobile())
                            .address(c.getUser().getUserDetail().getAddress())
                            .role(c.getUser().getRole().getName().name())
                            .status(c.getUser().getStatus())
                            .build();

                    dtoList.add(dto);
                });
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
                    {
                        put("CustomerList", dtoList);
                        put("SizePage", customers.getTotalPages());
                    }
                }));
            }
        }
    }

    @Override
    public ResponseEntity<JwtResponse> getOneAccountHostByAdmin(Long hostID) {

        paymentService.checkTimePostPayment();
        postVoucherService.checkTimePostVoucher();

        if (Objects.isNull(hostID)) {
            throw new NotFoundException("host_id request param null");
        } else {
            Host h = hostRepository.getHostsById(hostID);

            if (Objects.isNull(h)) {
                throw new NotFoundException("host_id not data");
            } else {
                HostDto dto = HostDto.builder()
                        .hostID(h.getId())
                        .userID(h.getUser().getId())
                        .userDetailID(h.getUser().getUserDetail().getUserDetailId())
                        .username(h.getUser().getUsername())
                        .email(h.getUser().getEmail())
                        .urlImage(h.getUser().getUserDetail().getAvatarUrl())
                        .roleName(h.getUser().getRole().getName().name())
                        .createDate(h.getUser().getCreateDate())
                        .dob(h.getUser().getUserDetail().getDob())
                        .fullName(h.getUser().getUserDetail().getFullName())
                        .mobile(h.getUser().getUserDetail().getMobile())
                        .address(h.getUser().getUserDetail().getAddress())
                        .totalFollower(followHostRepository.totalFollowHostByCustomer(h.getId()))
                        .typeAccountHost(h.getTypeAccount())
                        .status(h.getUser().getStatus())
                        .build();

                return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), dto));
            }
        }
    }

    @Override
    public ResponseEntity<JwtResponse> getOneAccountCustomerByAdmin(Long customerID) {

        paymentService.checkTimePostPayment();
        postVoucherService.checkTimePostVoucher();

        if (Objects.isNull(customerID)) {
            throw new NotFoundException("host_id request param null");
        } else {
            Customer c = customerRepository.getCustomerById(customerID);

            if (Objects.isNull(c)) {
                throw new NotFoundException("customer-id not data");
            } else {
                UserDto dto = UserDto.builder()
                        .customerID(c.getId())
                        .userID(c.getUser().getId())
                        .email(c.getUser().getEmail())
                        .username(c.getUser().getUsername())
                        .userDetailID(c.getUser().getUserDetail().getUserDetailId())
                        .urlImage(c.getUser().getUserDetail().getAvatarUrl())
                        .createDate(c.getUser().getCreateDate())
                        .dob(c.getUser().getUserDetail().getDob())
                        .fullName(c.getUser().getUserDetail().getFullName())
                        .mobile(c.getUser().getUserDetail().getMobile())
                        .address(c.getUser().getUserDetail().getAddress())
                        .role(c.getUser().getRole().getName().name())
                        .status(c.getUser().getStatus())
                        .build();
                return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), dto));
            }
        }
    }

    /*
      status = 0 chua active
      status = 1 tai khoan hoat dong
      status = 2 khoa tai khoan
     */
    @Override
    public ResponseEntity<MessageResponse> changeStatus(Long userID, int status) {

        paymentService.checkTimePostPayment();
        postVoucherService.checkTimePostVoucher();

        User user = userRepository.findUserById(userID);

        if (Objects.isNull(user)) {
            throw new NotFoundException("khong co data cua user_id");
        } else {
            if (status == 1) {
                user.setStatus(1);
            } else if (status == 2) {
                user.setStatus(2);
            }

            User updateStatus = userRepository.save(user);

            if (Objects.isNull(updateStatus)) {
                throw new UpdateDataException("Update status cua user_id khong thanh cong");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(HttpStatus.OK.value(), "update status thanh cong"));
            }
        }
    }
}
