package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.ListDetailRateDto;
import com.homesharing_backend.data.dto.PostTopRateDto;
import com.homesharing_backend.data.dto.ViewDetailRateDto;
import com.homesharing_backend.data.dto.ViewRateHostDto;
import com.homesharing_backend.data.entity.*;
import com.homesharing_backend.data.repository.*;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.exception.SaveDataException;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.ReportRequest;
import com.homesharing_backend.service.ManageRateService;
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
public class ManageRateServiceImpl implements ManageRateService {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private ReportRateRepository reportRateRepository;

    @Autowired
    private LikesDislikesRepository likesDislikesRepository;

    @Autowired
    private ReportTypeRepository reportTypeRepository;


    @Override
    public ResponseEntity<ResponseObject> getAllRateByHost(int indexPage) {

        int size = 10;
        int page = indexPage - 1;

        Host host = hostRepository.getHostsByUser_Id(SecurityUtils.getPrincipal().getId());
        Page<Post> postPage = postRepository.getPostByHost_Id(host.getId(), PageRequest.of(page, size));

        if (Objects.isNull(postPage)) {
            throw new NotFoundException("khong co post lien quan den host_id");
        } else {

            List<ViewRateHostDto> dtoList = new ArrayList<>();
            postPage.forEach(p -> {

                int totalRate = rateRepository.countRateByBookingDetail_Post_Id(p.getId());
                int totalReportRate = reportRateRepository.countReportRateByRate_BookingDetail_Post_Id(p.getId());

                ViewRateHostDto dto = ViewRateHostDto.builder()
                        .postID(p.getId())
                        .title(p.getTitle())
                        .statusPost(p.getStatus())
                        .totalRate(totalRate)
                        .totalReportRate(totalReportRate)
                        .build();
                dtoList.add(dto);
            });

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
                {
                    put("listPostRate", dtoList);
                    put("sizePage", postPage.getTotalPages());
                }
            }));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getAllDetailRateByHost(Long postID, int indexPage) {

        int size = 10;
        int page = indexPage - 1;

        Host host = hostRepository.getHostsByUser_Id(SecurityUtils.getPrincipal().getId());
        Post post = postRepository.getPostById(postID);


        if (Objects.isNull(post)) {
            throw new NotFoundException("khong co post lien quan den host_id");
        } else {
            PostTopRateDto postTopRateDto = postRepository.getPostDetailByPostIDByHost(post.getId());

            Page<Rate> ratePage = rateRepository.getRateByBookingDetail_Post_Id(post.getId(), PageRequest.of(page, size));

            if (Objects.isNull(ratePage)) {
                throw new NotFoundException("khong co rate lien quan den post_id");
            } else {
                List<ViewDetailRateDto> dtoList = new ArrayList<>();

                ratePage.forEach(r -> {

                    int totalLike = likesDislikesRepository.countLikesDislikesByRate_IdAndTypeAndStatus(r.getId(), 1, 1);
                    int totalDislike = likesDislikesRepository.countLikesDislikesByRate_IdAndTypeAndStatus(r.getId(), 2, 1);

                    ReportRate reportPost = reportRateRepository.getReportRateByRate_Id(r.getId());

                    ViewDetailRateDto dto = ViewDetailRateDto.builder()
                            .customerID(r.getBookingDetail().getBooking().getCustomer().getId())
                            .username(r.getBookingDetail().getBooking().getCustomer().getUser().getUsername())
                            .fullName(r.getBookingDetail().getBooking().getCustomer().getUser().getUserDetail().getFullName())
                            .urlImage(r.getBookingDetail().getBooking().getCustomer().getUser().getUserDetail().getAvatarUrl())
                            .star(r.getPoint())
                            .rateID(r.getId())
                            .comment(r.getComment())
                            .dateRate(r.getDateRate())
                            .statusRate(r.getStatus())
                            .totalLike(totalLike)
                            .totalDislike(totalDislike)
                            .build();

                    if(Objects.isNull(reportPost)){
                        dto.setStatusReportRate(1);
                    } else {
                        dto.setDescriptionReport(reportPost.getDescription());
                        dto.setStatusReportRate(0);
                    }

                    dtoList.add(dto);
                });

                ListDetailRateDto dto = ListDetailRateDto.builder()
                        .postID(post.getId())
                        .title(post.getTitle())
                        .statusPost(post.getStatus())
                        .detailRateDtoList(dtoList)
                        .build();

                if (Objects.isNull(postTopRateDto.getAvgRate())) {
                    dto.setAvgRate(0.0);
                } else {
                    dto.setAvgRate(postTopRateDto.getAvgRate());
                }

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
                    {
                        put("listDetailRate", dto);
                        put("sizePage", ratePage.getTotalPages());
                    }
                }));
            }
        }
    }

    @Override
    public ResponseEntity<MessageResponse> createReportRateByHost(Long rateID, ReportRequest reportRequest) {
        Rate rate = rateRepository.getRateById(rateID);

        if (Objects.isNull(rate)) {
            throw new NotFoundException("Rate_id khong ton tai");
        } else {
            Host host = hostRepository.getHostsByUser_Id(SecurityUtils.getPrincipal().getId());

            ReportType reportType = reportTypeRepository.getReportTypeById(reportRequest.getReportTypeID());

            if (Objects.isNull(reportType)) {
                throw new NotFoundException("ReportType-id khong ton tai");
            } else {

                ReportRate reportRate = ReportRate.builder()
                        .rate(rate)
                        .host(host)
                        .reportType(reportType)
                        .description(reportRequest.getDescription())
                        .status(1)
                        .build();

                ReportRate saveReportRate = reportRateRepository.save(reportRate);

                if (Objects.isNull(saveReportRate)) {
                    throw new SaveDataException("report rate khong thanh cong");
                } else {
                    return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(200, "report rate thanh cong"));
                }
            }
        }
    }
}
