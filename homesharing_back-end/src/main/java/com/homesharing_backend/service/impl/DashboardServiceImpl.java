package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.*;
import com.homesharing_backend.data.entity.Host;
import com.homesharing_backend.data.repository.*;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.service.DashboardService;
import com.homesharing_backend.service.PaymentService;
import com.homesharing_backend.service.PostVoucherService;
import com.homesharing_backend.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostPaymentRepository postPaymentRepository;

    @Autowired
    private BookingDetailRepository bookingDetailRepository;

    @Autowired
    private PostVoucherService postVoucherService;

    @Autowired
    private PaymentService paymentService;

    @Override
    public ResponseEntity<ResponseObject> dashboardAdmin() {

        paymentService.checkTimePostPayment();
        postVoucherService.checkTimePostVoucher();

        int totalAccount = userRepository.totalAccount();
        int totalCustomer = customerRepository.totalCustomer();
        int totalHost = hostRepository.totalHost();
        int totalPostActive = postRepository.totalPostActive(1);
        int totalPostDeActive = postRepository.totalPostDeActiveByAdmin();
        int totalPost = postRepository.totalPost();
        int totalHostDeActive = hostRepository.countHostByUser_Status(2);
        int totalCustomerDeActive = customerRepository.countCustomerByUser_Status(2);
        int totalPostPayment = postPaymentRepository.totalPricePostPayment();
        List<DashboardPostPaymentDto> paymentDtoList = postPaymentRepository.getAllPostPayment();

//        List<DashboardPostDto> dashboardPostDtoList = new ArrayList<>();
//
//        for (int i = 1; i < 13; i++) {
//            int month = postRepository.getAllMonth(i);
//            DashboardPostDto dto = DashboardPostDto.builder()
//                    .month(i)
//                    .totalPost(month)
//                    .build();
//            dashboardPostDtoList.add(dto);
//        }

        List<DashboardDto> list = new ArrayList<>();
        list.add(new DashboardDto("Bài đăng hoạt động", totalPostActive));
        list.add(new DashboardDto("Bài đăng dừng hoạt động", totalPostDeActive));

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
            {
                put("totalAccount", totalAccount);
                put("totalCustomer", totalCustomer);
                put("totalHost", totalHost);
//                put("totalPostByMonth", dashboardPostDtoList);
                put("post", list);
                put("totalPost", totalPost);
                put("totalHostDeActive", totalHostDeActive);
                put("totalCustomerDeActive", totalCustomerDeActive);
                put("totalPostPayment", totalPostPayment);
                put("paymentDtoList", paymentDtoList);
            }
        }));
    }

    @Override
    public ResponseEntity<ResponseObject> dashboardHost() {

        paymentService.checkTimePostPayment();
        postVoucherService.checkTimePostVoucher();

        Host host = hostRepository.getHostsByUser_Id(SecurityUtils.getPrincipal().getId());

        if (Objects.isNull(host)) {
            throw new NotFoundException("Host_id khong ton tai");
        } else {

            int totalPost = postRepository.countPostByHost_Id(host.getId());
            int totalPostActive = postRepository.countPostByHost_IdAndStatus(host.getId(), 1);
            int totalPostDeActive = postRepository.totalPostDeActive(host.getId());
            List<DashboardPostPaymentDto> paymentDtoList = postPaymentRepository.getAllPostPaymentByHost(host.getId());

            List<DashboardPostPaymentDto> list = new ArrayList<>();

            paymentDtoList.forEach(p -> {

                if (ObjectUtils.isEmpty(p.getTotalPost())) {
                    p.setTotalPost(Long.valueOf("0"));
                }
                list.add(p);
            });

            List<DashboardDto> dtoList = new ArrayList<>();
            dtoList.add(new DashboardDto("Bài đăng hoạt động", totalPostActive));
            dtoList.add(new DashboardDto("Bài đăng dừng hoạt động", totalPostDeActive));

            List<DashboardBookingDto> bookingDtoList = bookingDetailRepository.totalBookingByHost(host.getId(), 4);

            List<DashboardPostDto> dashboardPostDtoList = new ArrayList<>();

            for (int i = 1; i < 13; i++) {
                int month = postRepository.getAllMonth(i, host.getId());
                DashboardPostDto dto = DashboardPostDto.builder()
                        .month(i)
                        .totalPost(month)
                        .build();
                dashboardPostDtoList.add(dto);
            }

            List<DashboardPaymentPostDto> totalPriceByPost = postPaymentRepository.getAllPaymentByHost(host.getId());

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
                {
                    put("totalPost", totalPost);
                    put("post", dtoList);
                    put("totalPostPayment", list);
                    put("totalBooking", bookingDtoList);
                    put("totalPostByMonth", dashboardPostDtoList);
                    put("totalPriceByPost", totalPriceByPost);
                }
            }));
        }
    }
}
