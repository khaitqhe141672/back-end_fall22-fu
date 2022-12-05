package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.DashboardBookingDto;
import com.homesharing_backend.data.dto.DashboardPostDto;
import com.homesharing_backend.data.dto.DashboardPostPaymentDto;
import com.homesharing_backend.data.entity.Host;
import com.homesharing_backend.data.repository.*;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.service.DashboardService;
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

    @Override
    public ResponseEntity<ResponseObject> dashboardAdmin() {

        int totalAccount = userRepository.totalAccount();
        int totalCustomer = customerRepository.totalCustomer();
        int totalHost = hostRepository.totalHost();
        int totalPostActive = postRepository.totalPostActive(2);
        int totalPostDeActive = postRepository.totalPostActive(3);
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

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
            {
                put("totalAccount", totalAccount);
                put("totalCustomer", totalCustomer);
                put("totalHost", totalHost);
//                put("totalPostByMonth", dashboardPostDtoList);
                put("totalPostActive", totalPostActive);
                put("totalPostDeActive", totalPostDeActive);
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

            List<DashboardBookingDto> bookingDtoList = bookingDetailRepository.totalBookingByHost(host.getId(), 4);


            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
                {
                    put("totalPost", totalPost);
                    put("totalPostActive", totalPostActive);
                    put("totalPostDeActive", totalPostDeActive);
                    put("totalPostPayment", list);
                    put("totalBooking", bookingDtoList);
                }
            }));
        }
    }
}