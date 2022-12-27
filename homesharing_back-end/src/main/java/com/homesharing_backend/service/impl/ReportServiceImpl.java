package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.*;
import com.homesharing_backend.data.entity.*;
import com.homesharing_backend.data.repository.*;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.exception.SaveDataException;
import com.homesharing_backend.exception.SendMailException;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.ComplaintRequest;
import com.homesharing_backend.presentation.payload.request.ReportRequest;
import com.homesharing_backend.presentation.payload.request.UpdateReportPostRequest;
import com.homesharing_backend.service.ReportService;
import com.homesharing_backend.util.JavaMail;
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
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRateRepository reportRateRepository;

    @Autowired
    private ReportPostRepository reportPostRepository;

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ReportTypeRepository reportTypeRepository;

    @Autowired
    private ComplaintPostRepository complaintPostRepository;

    @Autowired
    private ComplaintRateRepository complaintRateRepository;

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private PostImageRepository postImageRepository;

    @Autowired
    private HistoryHandleReportPostRepository historyHandleReportPostRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private HistoryHandleReportPostDetailRepository historyHandleReportPostDetailRepository;

    @Override
    public ResponseEntity<MessageResponse> createReportRate(ReportRequest reportRequest, Long rateID) {

        Rate rate = rateRepository.getRateById(rateID);

        if (Objects.isNull(rate)) {
            throw new NotFoundException("rate-id khong ton tai");
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
                    throw new SaveDataException("report post khong thanh cong");
                } else {
                    return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(200, "report post thanh cong"));
                }
            }
        }
    }

    @Override
    public ResponseEntity<MessageResponse> createReportPost(ReportRequest reportRequest, Long postID, Long bookingID) {

        Post post = postRepository.getPostById(postID);

        if (Objects.isNull(post)) {
            throw new NotFoundException("Post-id khong ton tai");
        } else {
            Customer customer = customerRepository.getCustomerByUser_Id(SecurityUtils.getPrincipal().getId());

            ReportType reportType = reportTypeRepository.getReportTypeById(reportRequest.getReportTypeID());
            Booking booking = bookingRepository.getBookingById(bookingID);

            if (Objects.isNull(reportType)) {
                throw new NotFoundException("ReportType-id khong ton tai");
            } else {
                ReportPost reportRate = ReportPost.builder()
                        .post(post)
                        .customer(customer)
                        .reportType(reportType)
                        .description(reportRequest.getDescription())
                        .booking(booking)
                        .status(1)
                        .build();

                ReportPost saveReportPost = reportPostRepository.save(reportRate);

                if (reportPostRepository.countReportPostByPost_IdAndStatus(post.getId(), 1) == 3) {
                    post.setStatusReport(2);
                    post.setStatus(3);
                    postRepository.save(post);
                    String toEmail = post.getHost().getUser().getEmail();
                    String subject = "Thông báo tạm thời ẩn bài đăng";
                    String text = "Kính gửi anh/chị " + post.getHost().getUser().getUserDetail().getFullName() + ",\n" +
                            "Home Sharing trân trọng thông báo về tình trạng bài đăng '" + post.getTitle() + "' đã bị khách hàng" +
                            " báo cáo 3 lần về chất lượng và dịch vụ tại homestay. \n" +
                            "Hệ thông chúng tôi đang tạm thời ẩn bài, bạn có thể truy cập vào hệ thống để thực hiện việc" +
                            " khiếu nại. Chúng tôi sẽ xem xét và xử lý.\n\n" +
                            "Trân trạng\n\n" +
                            "Đội ngũ Home Sharing." ;
                    try {
                        new JavaMail().sentEmail(toEmail, subject, text);
                    } catch (Exception e) {
                        throw new SendMailException("Không gửi được email");
                    }
                }

                if (Objects.isNull(saveReportPost)) {
                    throw new SaveDataException("report post khong thanh cong");
                } else {
                    return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(200, "report post thanh cong"));
                }
            }
        }
    }

    /*status = 1 dang cho admin xu ly*/
    @Override
    public ResponseEntity<MessageResponse> createComplaintPost(ComplaintRequest complaintRequest,
                                                               Long postID, Long historyID) {

        Post post = postRepository.getPostById(postID);

        HistoryHandleReportPost reportPost =
                historyHandleReportPostRepository.getHistoryHandleReportPostByIdAndPost_Id(historyID, post.getId());

        if (Objects.isNull(post)) {
            throw new NotFoundException("post-id khong ton tai");
        } else {
            Host host = hostRepository.getHostsByUser_Id(SecurityUtils.getPrincipal().getId());

            ComplaintPost complaintPost = ComplaintPost.builder()
                    .description(complaintRequest.getDescription())
                    .host(host)
                    .post(post)
                    .statusPost(reportPost.getStatusPost())
                    .historyHandleReportPost(reportPost)
                    .status(1)
                    .build();

            ComplaintPost saveComplaintPost = complaintPostRepository.save(complaintPost);

            if (Objects.isNull(saveComplaintPost)) {
                throw new SaveDataException("Complaint post khong thanh cong");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(200, "Complaint post thanh cong"));
            }
        }
    }

    /*type = 1 khang an thanh cong update status= 2
      type = 2 khang an khong thanh cong khong phai lam gi*/
    @Override
    public ResponseEntity<MessageResponse> resolveComplaintPost(Long complaintPostID, int status, int statusPost) {

        ComplaintPost complaintPost = complaintPostRepository.getComplaintPostById(complaintPostID);

        if (Objects.isNull(complaintPost)) {
            throw new NotFoundException("ComplaintPost-id khong ton tai");
        } else {
            Post post = postRepository.getPostById(complaintPost.getPost().getId());

            complaintPost.setStatus(status);
            complaintPost.setStatusPost(statusPost);
            complaintPostRepository.save(complaintPost);

            post.setStatusReport(statusPost);
            post.setStatus(statusPost);
            postRepository.save(post);

            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(200, "Khang an report post thanh cong"));

        }
    }

    /*type = 1 khang an thanh cong update status ve 2
      type = 2 khang an khong thanh cong khong phai lam gi*/
    @Override
    public ResponseEntity<MessageResponse> resolveComplaintRate(Long complaintRateID, int type) {

        ComplaintRate complaintRate = complaintRateRepository.getComplaintRateById(complaintRateID);

        if (Objects.isNull(complaintRate)) {
            throw new NotFoundException("ComplaintRate-id khong ton tai");
        } else {
            if (type == 2) {
                return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(200, "Khang an report rate khong thanh cong"));
            } else {

                complaintRate.setStatus(2);
                complaintRateRepository.save(complaintRate);

                ReportRate reportRate = reportRateRepository.getReportRateById(complaintRate.getReportRate().getId());
                reportRate.setStatus(2);
                reportRateRepository.save(reportRate);

                return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(200, "Khang an report rate thanh cong"));
            }
        }

    }

    @Override
    public ResponseEntity<ResponseObject> getAllReportRateByAdmin(int indexPage) {

        if (Objects.isNull(indexPage)) {
            throw new NotFoundException("IndexPage null");
        } else {
            int size = 10;
            int page = indexPage - 1;

            Page<ReportRate> reportRates = reportRateRepository.findAll(PageRequest.of(page, size));

            if (Objects.isNull(reportRates)) {
                throw new NotFoundException("khong co data voi indexPage");
            } else {

                List<ReportDto> reportDtoList = new ArrayList<>();

                reportRates.forEach(r -> {
                    ReportDto dto = ReportDto.builder()
                            .reportTypeID(r.getReportType().getId())
                            .reportID(r.getId())
                            .description(r.getDescription())
                            .fullName(r.getHost().getUser().getUserDetail().getFullName())
                            .nameReportType(r.getReportType().getName())
                            .username(r.getHost().getUser().getUsername())
                            .imageUrl(r.getHost().getUser().getUserDetail().getAvatarUrl())
                            .status(r.getStatus())
                            .build();

                    reportDtoList.add(dto);
                });
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
                    {
                        put("ReportRate", reportDtoList);
                        put("SizePage", reportRates.getTotalPages());
                    }
                }));
            }
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getAllReportPostByAdmin(int indexPage) {

        if (Objects.isNull(indexPage)) {
            throw new NotFoundException("index page null");
        } else {
            int size = 10;
            int page = indexPage - 1;

            Page<ReportPost> reportPosts = reportPostRepository.findAll(PageRequest.of(page, size));

            if (Objects.isNull(reportPosts)) {
                throw new NotFoundException("khong co data voi indexPage");
            } else {
                List<ReportDto> reportDtoList = new ArrayList<>();

                reportPosts.forEach(r -> {
                    ReportDto dto = ReportDto.builder()
                            .reportTypeID(r.getReportType().getId())
                            .reportID(r.getId())
                            .description(r.getDescription())
                            .nameReportType(r.getReportType().getName())
                            .username(r.getCustomer().getUser().getUsername())
                            .imageUrl(r.getCustomer().getUser().getUserDetail().getAvatarUrl())
                            .status(r.getStatus())
                            .build();

                    reportDtoList.add(dto);
                });
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
                    {
                        put("ReportPost", reportDtoList);
                        put("SizePage", reportPosts.getTotalPages());
                    }
                }));
            }
        }
    }

    @Override
    public ResponseEntity<JwtResponse> getAllReportPostByHost(Long postID) {

        if (Objects.isNull(postID)) {
            throw new NotFoundException("postID null");
        } else {
            List<ReportPost> reportPosts = reportPostRepository.getReportPostByPost_Id(postID);

            if (Objects.isNull(reportPosts)) {
                throw new NotFoundException("reportPosts null");
            } else {
                List<ReportDto> reportPostDtoList = new ArrayList<>();

                reportPosts.forEach(r -> {
                    ReportDto dto = ReportDto.builder()
                            .reportID(r.getId())
                            .username(r.getCustomer().getUser().getUsername())
                            .imageUrl(r.getCustomer().getUser().getUserDetail().getAvatarUrl())
                            .description(r.getDescription())
                            .reportTypeID(r.getReportType().getId())
                            .nameReportType(r.getReportType().getName())
                            .status(r.getStatus())
                            .build();

                    reportPostDtoList.add(dto);
                });
                return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), reportPostDtoList));
            }
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getAllReportPostByPostOfHost(int indexPage) {

        if (Objects.isNull(indexPage)) {
            throw new NotFoundException("index page null");
        } else {
            int size = 10;
            int page = indexPage - 1;

            Page<ReportPostDto> posts = reportPostRepository.listAllReportPostByHost(PageRequest.of(page, size));

            if (Objects.isNull(posts)) {
                throw new NotFoundException("khong post ton tai");
            } else {

                List<ReportPostDto> reportPostDtoList = new ArrayList<>();

                posts.forEach(p -> {

                    List<Long> list = new ArrayList<>();

                    List<ReportPost> reportPosts = reportPostRepository.getReportPostByPost_IdAndStatus(p.getPostID(), 1);
                    if (!Objects.isNull(reportPosts)) {
                        reportPosts.forEach(rp -> {
                            list.add(rp.getId());
                        });
                    }

                    int totalReport = reportPostRepository.countReportPostByPost_Id(p.getPostID());

                    p.setTotalReport(totalReport);
                    p.setListReportPostID(list);
                    reportPostDtoList.add(p);
                });
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
                    {
                        put("reportPostList", reportPostDtoList);
                        put("sizePage", posts.getTotalPages());
                        put("size", indexPage);
                    }
                }));
            }
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getAllDetailReportPostByPostIDOfHost(Long postID, int indexPage) {

        if (Objects.isNull(indexPage)) {
            throw new NotFoundException("index page null");
        } else {
            int size = 10;
            int page = indexPage - 1;

            Page<ReportPost> reportPosts = reportPostRepository.findReportPostByPost_Id(postID, PageRequest.of(page, size));

            if (Objects.isNull(reportPosts)) {
                throw new NotFoundException("khong co data");
            } else {

                List<ReportDto> reportDtoList = new ArrayList<>();

                reportPosts.forEach(r -> {

                    ReportDto dto = ReportDto.builder()
                            .reportID(r.getId())
                            .fullName(r.getCustomer().getUser().getUserDetail().getFullName())
                            .username(r.getCustomer().getUser().getUsername())
                            .imageUrl(r.getCustomer().getUser().getUserDetail().getAvatarUrl())
                            .description(r.getDescription())
                            .reportTypeID(r.getReportType().getId())
                            .nameReportType(r.getReportType().getName())
                            .build();

                    reportDtoList.add(dto);
                });
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
                    {
                        put("detailReportPost", reportDtoList);
                        put("sizePage", reportPosts.getTotalPages());
                        put("size", indexPage);
                    }
                }));
            }
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getAllComplaintRateByAdmin(int indexPage) {

        int size = 10;
        int page = indexPage - 1;

        Page<ComplaintRate> complaintRates = complaintRateRepository.findAll(PageRequest.of(page, size));

        if (Objects.isNull(complaintRates)) {
            throw new NotFoundException("khong co data khieu nai");
        } else {
            List<ComplaintDto> complaintDtoList = new ArrayList<>();

            complaintRates.forEach(r -> {

                ReportRate rate = reportRateRepository.getReportRateById(r.getReportRate().getId());

                ComplaintDto dto = ComplaintDto.builder()
                        .fullName(r.getHost().getUser().getUserDetail().getFullName())
                        .username(r.getHost().getUser().getUsername())
                        .imageUrl(r.getHost().getUser().getUserDetail().getAvatarUrl())
                        .descriptionComplaint(r.getDescription())
                        .statusComplaint(r.getStatus())
                        .build();

                complaintDtoList.add(dto);
            });
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
                {
                    put("listComplaint", complaintDtoList);
                    put("sizePage", complaintRates.getTotalPages());
                    put("size", indexPage);
                }
            }));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getAllComplaintPostByAdmin(int indexPage) {

        int size = 10;
        int page = indexPage - 1;

        Page<ComplaintPost> complaintPosts = complaintPostRepository.findAll(PageRequest.of(page, size));

        if (Objects.isNull(complaintPosts)) {
            throw new NotFoundException("khong co data khieu nai");
        } else {

            List<ComplaintDto> complaintDtoList = new ArrayList<>();

            complaintPosts.forEach(r -> {

                ComplaintDto dto = ComplaintDto.builder()
                        .postID(r.getPost().getId())
                        .title(r.getPost().getTitle())
                        .complaintPostID(r.getId())
                        .fullName(r.getHost().getUser().getUserDetail().getFullName())
                        .username(r.getHost().getUser().getUsername())
                        .imageUrl(r.getHost().getUser().getUserDetail().getAvatarUrl())
                        .descriptionComplaint(r.getDescription())
                        .statusComplaint(r.getStatus())
                        .statusPost(r.getStatusPost())
                        .build();

                complaintDtoList.add(dto);
            });
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
                {
                    put("listComplaint", complaintDtoList);
                    put("sizePage", complaintPosts.getTotalPages());
                    put("size", indexPage);
                }
            }));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getAllComplaintPostByHost(int indexPage) {

        Host host = hostRepository.getHostsByUser_Id(SecurityUtils.getPrincipal().getId());

        int size = 10;
        int page = indexPage - 1;

        Page<ComplaintPost> complaintPosts = complaintPostRepository.getComplaintPostByHost_Id(host.getId(), PageRequest.of(page, size));

        if (Objects.isNull(complaintPosts)) {
            throw new NotFoundException("khong co khieu nai nao cua host");
        } else {
            List<ComplaintDto> complaintDtoList = new ArrayList<>();

            complaintPosts.forEach(r -> {

                ComplaintDto dto = ComplaintDto.builder()
                        .fullName(r.getHost().getUser().getUserDetail().getFullName())
                        .username(r.getHost().getUser().getUsername())
                        .imageUrl(r.getHost().getUser().getUserDetail().getAvatarUrl())
                        .descriptionComplaint(r.getDescription())
                        .statusComplaint(r.getStatus())
                        .build();

                complaintDtoList.add(dto);
            });
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
                {
                    put("listComplaint", complaintDtoList);
                    put("sizePage", complaintPosts.getTotalPages());
                    put("size", indexPage);
                }
            }));
        }
    }

    @Override
    public ResponseEntity<MessageResponse> updateStatusReportRate(Long reportRateID) {

        ReportRate reportRate = reportRateRepository.getReportRateById(reportRateID);

        if (Objects.isNull(reportRate)) {
            throw new NotFoundException("report-rate-id khong ton tai");
        } else {

            Rate rate = rateRepository.getRateById(reportRate.getRate().getId());
            rate.setStatus(2);
            rateRepository.save(rate);

            reportRate.setStatus(2);
            reportRateRepository.save(reportRate);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(HttpStatus.OK.value(), "update status thanh cong"));
        }
    }

    @Override
    public ResponseEntity<MessageResponse> updateStatusReportPost(UpdateReportPostRequest updateReportPostRequest,
                                                                  Long postID, int status) {

        Post post = postRepository.getPostById(postID);

        if (!Objects.isNull(post)) {
            HistoryHandleReportPost historyHandleReportPost = HistoryHandleReportPost.builder()
                    .statusReport(2)
                    .statusPost(status)
                    .post(post)
                    .build();
            HistoryHandleReportPost saveHistory = historyHandleReportPostRepository.save(historyHandleReportPost);

            updateReportPostRequest.getListReportPostID().forEach(r -> {

                ReportPost reportPost = reportPostRepository.getReportPostByIdAndPost_Id(r, post.getId());

                if (!Objects.isNull(reportPost)) {

                    HistoryHandleReportPostDetail historyHandleReportPostDetail = HistoryHandleReportPostDetail.builder()
                            .historyHandleReportPost(saveHistory)
                            .reportPost(reportPost)
                            .statusHistory(1)
                            .build();

                    historyHandleReportPostDetailRepository.save(historyHandleReportPostDetail);
                    reportPost.setStatus(2);
                    post.setStatus(status);
                    post.setStatusReport(2);
                    reportPostRepository.save(reportPost);
                    postRepository.save(post);
                }
            });

            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(HttpStatus.OK.value(), "update status thanh cong"));
        } else {
            throw new NotFoundException("khong co post-id nay");
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getAllReportPostStatusDoneByHost(int indexPage, Long postID) {

        int size = 10;
        int page = indexPage - 1;

        Post post = postRepository.getPostById(postID);

        if (Objects.isNull(post)) {
            throw new NotFoundException("post null");
        } else {

            Page<ReportPost> reportPosts = reportPostRepository.findReportPostByPost_IdAndStatus(post.getId(), 2, PageRequest.of(page, size));

            if (Objects.isNull(reportPosts)) {
                throw new NotFoundException("post null");
            } else {
                List<ReportDto> reportPostDtoList = new ArrayList<>();

                reportPosts.forEach(r -> {
                    ReportDto dto = ReportDto.builder()
                            .reportID(r.getId())
                            .username(r.getCustomer().getUser().getUsername())
                            .imageUrl(r.getCustomer().getUser().getUserDetail().getAvatarUrl())
                            .description(r.getDescription())
                            .reportTypeID(r.getReportType().getId())
                            .nameReportType(r.getReportType().getName())
                            .status(r.getStatus())
                            .build();

                    reportPostDtoList.add(dto);
                });
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
                    {
                        put("listReportPost", reportPostDtoList);
                        put("sizePage", reportPosts.getTotalPages());
                        put("size", indexPage);
                    }
                }));
            }
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getAllHistoryReportPost(Long postID, int indexPage) {

        int size = 10;
        int page = indexPage - 1;

        Page<HistoryHandleReportPost> historyHandleReportPosts =
                historyHandleReportPostRepository.getHistoryHandleReportPostByPost_Id(postID, PageRequest.of(page, size));

        if (Objects.isNull(historyHandleReportPosts)) {
            throw new NotFoundException("Khong co data cua history");
        } else {
            List<HistoryReportPostDto> list = new ArrayList<>();
            historyHandleReportPosts.forEach(h -> {

                int totalReport = historyHandleReportPostDetailRepository.countHistoryHandleReportPostDetailByHistoryHandleReportPost_Id(h.getId());

                HistoryReportPostDto dto = HistoryReportPostDto.builder()
                        .historyHandleReportPostID(h.getId())
                        .statusReportPost(h.getStatusReport())
                        .statusPost(h.getStatusPost())
                        .postID(h.getPost().getId())
                        .title(h.getPost().getTitle())
                        .totalReportPost(totalReport)
                        .build();

                list.add(dto);
            });
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
                {
                    put("listHistoryReportPost", list);
                    put("sizePage", historyHandleReportPosts.getTotalPages());
                    put("size", indexPage);
                }
            }));
        }

    }

    @Override
    public ResponseEntity<ResponseObject> getAllHistoryReportPostDetail(Long historyID, int indexPage) {

        int size = 10;
        int page = indexPage - 1;

        Page<HistoryHandleReportPostDetail> details =
                historyHandleReportPostDetailRepository.getHistoryHandleReportPostDetailByHistoryHandleReportPost_Id(historyID, PageRequest.of(page, size));

        if (Objects.isNull(details)) {
            throw new NotFoundException("history-id khogn co data");
        } else {

            List<ListHistoryHandleReportDto> list = new ArrayList<>();

            details.forEach(d -> {
                ListHistoryHandleReportDto dto = ListHistoryHandleReportDto.builder()
                        .reportPostID(d.getReportPost().getId())
                        .statusHistory(d.getStatusHistory())
                        .reportTypeName(d.getReportPost().getReportType().getName())
                        .reportTypeID(d.getReportPost().getReportType().getId())
                        .description(d.getReportPost().getDescription())
                        .fullName(d.getReportPost().getCustomer().getUser().getUserDetail().getFullName())
                        .username(d.getReportPost().getCustomer().getUser().getUsername())
                        .imageUrl(d.getReportPost().getCustomer().getUser().getUserDetail().getAvatarUrl())
                        .build();

                list.add(dto);
            });
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
                {
                    put("listDetailHistoryReportPost", list);
                    put("sizePage", details.getTotalPages());
                    put("size", indexPage);
                }
            }));
        }
    }

    /*status = 1 dang cho admin xu ly*/
    @Override
    public ResponseEntity<MessageResponse> createComplaintRate(ComplaintRequest complaintRequest, Long reportRateID) {

        if (Objects.isNull(reportRateID)) {
            throw new NotFoundException("ReportRateID null");
        } else {
            ReportRate reportRate = reportRateRepository.getReportRateById(reportRateID);

            if (Objects.isNull(reportRateID)) {
                throw new NotFoundException("ReportRate-id khong ton tai");
            } else {
                Host host = hostRepository.getHostsByUser_Id(SecurityUtils.getPrincipal().getId());

                ComplaintRate complaintRate = ComplaintRate.builder()
                        .reportRate(reportRate)
                        .description(complaintRequest.getDescription())
                        .host(host)
                        .status(1)
                        .build();

                ComplaintRate saveComplaintRate = complaintRateRepository.save(complaintRate);

                if (Objects.isNull(saveComplaintRate)) {
                    throw new SaveDataException("Complaint rate khong thanh cong");
                } else {
                    return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(200, "Complaint rate thanh cong"));
                }
            }
        }
    }
}
