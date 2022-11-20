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
        return null;
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

        Page<ViewBookingDto> viewBookingDtoPage = postRepository.getAllCurrentBooking(status, host.getId(), PageRequest.of(page, size));

        if (Objects.isNull(viewBookingDtoPage)) {
            throw new NotFoundException("khong co phong nao book hnay");
        } else {

            List<CurrentBookingDto> dtoList = new ArrayList<>();

            viewBookingDtoPage.forEach(v -> {

                List<BookingServiceDto> list = bookingServiceRepository.getAllBookingService(v.getBookingID());
                CurrentBookingDto dto = CurrentBookingDto.builder()
                        .viewBookingDto(v)
                        .bookingServiceDtos(list)
                        .build();

                dtoList.add(dto);
            });
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
                {
                    put("listBooking", dtoList);
                    put("sizePage", viewBookingDtoPage.getTotalPages());
                }
            }));
        }
    }

}
