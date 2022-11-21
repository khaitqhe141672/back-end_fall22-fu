package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.*;
import com.homesharing_backend.data.entity.*;
import com.homesharing_backend.data.repository.*;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.service.ManagePostService;
import com.homesharing_backend.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class ManagePostServiceImpl implements ManagePostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostDetailRepository postDetailRepository;

    @Autowired
    private PostPaymentRepository postPaymentRepository;

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private ReportPostRepository reportPostRepository;

    @Autowired
    private BookingServiceRepository bookingServiceRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public ResponseEntity<ResponseObject> getAllPostByAdmin(int indexPage) {

        int size = 10;
        int page = indexPage - 1;

        Page<Post> postList = postRepository.findAll(PageRequest.of(page, size));
        List<PostView> postDtoList = new ArrayList<>();

        postList.forEach(p -> {
            PostDetail postDetail = postDetailRepository.getPostDetailByPost_Id(p.getId());
            PostPayment postPayment = postPaymentRepository.getTimePost(p.getId());

            PostView dto = PostView.builder()
                    .postID(p.getId())
                    .title(p.getTitle())
                    .statusPost(p.getStatus())
                    .build();

            if (!Objects.isNull(postPayment)) {
                dto.setEndDate(postPayment.getEndDate());
                dto.setStartDate(postPayment.getStartDate());
                dto.setStatusPostPayment(postPayment.getStatus());
            } else {
                dto.setStatusPostPayment(0);
            }

            postDtoList.add(dto);
        });
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
            {
                put("List-Post", postDtoList);
                put("SizePage", postList.getTotalPages());
            }
        }));
    }

    @Override
    public ResponseEntity<MessageResponse> checkPaymentPostByAdmin() {

        List<Post> postList = postRepository.findAll();

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = localDateTime.toLocalDate();
        Date nowDate = Date.valueOf(localDate);

        if (Objects.isNull(postList)) {
            throw new NotFoundException("khong co post nao");
        } else {
            postList.forEach(p -> {
                PostPayment postPayment = postPaymentRepository.getPostPaymentByPost_IdAndStatus(p.getId(), 1);
                if (!Objects.isNull(postPayment)) {
                    if (nowDate.after(postPayment.getEndDate())) {
                        postPayment.setStatus(2);
                        postPaymentRepository.save(postPayment);
                    }
                }
            });
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(200, "check post-payment success"));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getAllPostByHost(int indexPage) {

        Host host = hostRepository.getHostsByUser_Id(SecurityUtils.getPrincipal().getId());

        int size = 10;
        int page = indexPage - 1;

        Page<PostDto> postDtoList = postRepository.listPostByHost(host.getId(), PageRequest.of(page, size));

        if (Objects.isNull(postDtoList)) {
            throw new NotFoundException("khong co data");
        } else {

            List<PostDto> postDto = new ArrayList<>();

            postDtoList.forEach(p -> {
                PostPayment postPayment = postPaymentRepository.getTimePost(p.getPostID());
                PostDto dto = PostDto.builder()
                        .postID(p.getPostID())
                        .title(p.getTitle())
                        .status(p.getStatus())
                        .build();

                if (!Objects.isNull(postPayment)) {
                    dto.setEndDate(postPayment.getEndDate());
                    dto.setStartDate(postPayment.getStartDate());
                    dto.setStatusPostPayment(postPayment.getStatus());
                } else {
                    dto.setStatusPostPayment(0);
                }

                if (Objects.isNull(p.getAvgRate())) {
                    dto.setAvgRate(0.0);
                } else {
                    dto.setAvgRate(p.getAvgRate());
                }

                postDto.add(dto);
            });


            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
                {
                    put("listPost", postDto);
                    put("SizePage", postDtoList.getTotalPages());
                    put("indexPage", indexPage);
                }
            }));
        }

    }

    @Override
    public ResponseEntity<ResponseObject> getAllReportPostByHost(int indexPage, Long postID) {

        int size = 5;
        int page = indexPage - 1;

        Page<ReportPost> reportPosts = reportPostRepository.findReportPostByPost_Id(postID, PageRequest.of(page, size));

        if (Objects.isNull(reportPosts)) {
            throw new NotFoundException("data null");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
                {
                    put("listReportPost", reportPosts);
                    put("SizePage", reportPosts.getTotalPages());
                }
            }));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getAllBookingByHost(int indexPage, int status) {

        Host host = hostRepository.getHostsByUser_Id(SecurityUtils.getPrincipal().getId());

        int size = 10;
        int page = indexPage - 1;

        Page<ViewBookingDto> viewBookingDtoPage = postRepository.getAllStatusBooking(status, host.getId(), PageRequest.of(page, size));

        if (Objects.isNull(viewBookingDtoPage)) {
            throw new NotFoundException("khong co phong nao book hnay");
        } else {

            List<CurrentBookingDto> dtoList = new ArrayList<>();

            viewBookingDtoPage.forEach(v -> {

                Booking b = bookingRepository.getBookingById(v.getBookingID());

                UserBookingDto bookingDto = UserBookingDto.builder()
                        .customerID(b.getCustomer().getId())
                        .userID(b.getCustomer().getUser().getId())
                        .fullName(b.getCustomer().getUser().getUserDetail().getFullName())
                        .urlImage(b.getCustomer().getUser().getUserDetail().getAvatarUrl())
                        .username(b.getCustomer().getUser().getUsername())
                        .build();

                List<BookingServiceDto> list = bookingServiceRepository.getAllBookingService(v.getBookingID());
                CurrentBookingDto dto = CurrentBookingDto.builder()
                        .viewBookingDto(v)
                        .bookingServiceDtos(list)
                        .userBookingDto(bookingDto)
                        .build();

                dtoList.add(dto);
            });
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
                {
                    put("listBooking", dtoList);
                    put("sizePage", viewBookingDtoPage.getTotalPages());
                    put("totalBooking", viewBookingDtoPage.getTotalElements());
                }
            }));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getTotalBookingStatusByHost() {

        int totalCurrentBooking = bookingRepository.countBookingByStatus(3);
        int totalComingBooking = bookingRepository.countBookingByStatus(2);
        int totalConfirmBooking = bookingRepository.countBookingByStatus(1);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
            {
                put("totalCurrentBooking", totalCurrentBooking);
                put("totalComingBooking", totalComingBooking);
                put("totalConfirmBooking", totalConfirmBooking);
            }
        }));
    }

    @Override
    public ResponseEntity<ResponseObject> getCurrentBooking(int indexPage) {

        Host host = hostRepository.getHostsByUser_Id(SecurityUtils.getPrincipal().getId());

        int size = 10;
        int page = indexPage - 1;

        Page<ViewBookingDto> viewBookingDtoPage = postRepository.getAllCurrentBooking(host.getId(), PageRequest.of(page, size));

        if (Objects.isNull(viewBookingDtoPage)) {
            throw new NotFoundException("khong co phong nao book hnay");
        } else {

            List<CurrentBookingDto> dtoList = new ArrayList<>();

            viewBookingDtoPage.forEach(v -> {

                Booking b = bookingRepository.getBookingById(v.getBookingID());

                UserBookingDto bookingDto = UserBookingDto.builder()
                        .customerID(b.getCustomer().getId())
                        .userID(b.getCustomer().getUser().getId())
                        .fullName(b.getCustomer().getUser().getUserDetail().getFullName())
                        .urlImage(b.getCustomer().getUser().getUserDetail().getAvatarUrl())
                        .username(b.getCustomer().getUser().getUsername())
                        .build();

                List<BookingServiceDto> list = bookingServiceRepository.getAllBookingService(v.getBookingID());
                CurrentBookingDto dto = CurrentBookingDto.builder()
                        .viewBookingDto(v)
                        .bookingServiceDtos(list)
                        .userBookingDto(bookingDto)
                        .build();

                dtoList.add(dto);
            });
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
                {
                    put("listBooking", dtoList);
                    put("sizePage", viewBookingDtoPage.getTotalPages());
                    put("totalBooking", viewBookingDtoPage.getTotalElements());
                }
            }));
        }
    }

}
