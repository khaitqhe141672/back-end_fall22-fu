package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.HomeDto;
import com.homesharing_backend.data.dto.PostDto;
import com.homesharing_backend.data.dto.PostTopRateDto;
import com.homesharing_backend.data.entity.*;
import com.homesharing_backend.data.repository.*;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.exception.SaveDataException;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.PostRequest;
import com.homesharing_backend.service.PostService;
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
import java.util.*;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private PostDetailRepository postDetailRepository;

    @Autowired
    private PostImageRepository postImageRepository;

    @Autowired
    private UtilityRepository utilityRepository;

    @Autowired
    private PostUtilityRepository postUtilityRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private PostVoucherRepository postVoucherRepository;

    @Autowired
    private PaymentPackageRepository paymentPackageRepository;

    @Autowired
    private PostPaymentRepository postPaymentRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private PostServiceRepository postServiceRepository;

    @Autowired
    private HistoryPostPaymentRepository historyPostPaymentRepository;

    @Override
    public ResponseEntity<JwtResponse> getInterestingPlaceByPost() {

        List<Post> postList = postRepository.getPostTop();

        if (postList.isEmpty()) {
            throw new NotFoundException("không có dữ liệu");
        } else {

            List<HomeDto> homeDtoList = new ArrayList<>();

            postList.forEach(p -> {
                List<PostImage> image = postImageRepository.getPostImageByPost_Id(p.getId(), PageRequest.of(0, 1));
                HomeDto dto = HomeDto.builder()
                        .postID(p.getId())
                        .title(p.getTitle())
                        .build();

                if (image.size() > 0) {
                    dto.setUrlImage(image.get(0).getImageUrl());
                }

                homeDtoList.add(dto);
            });

            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), homeDtoList));
        }
    }

    @Override
    public ResponseEntity<JwtResponse> getTopPostByRate() {

        Page<PostTopRateDto> postTopRateDtos = postRepository.getTopPostByRate(PageRequest.of(0, 7));

        if (postTopRateDtos.isEmpty()) {
            throw new NotFoundException("không có dữ liệu");
        } else {
            List<HomeDto> homeDtoList = new ArrayList<>();

            postTopRateDtos.forEach(p ->{
                List<PostImage> image = postImageRepository.getPostImageByPost_Id(p.getId(), PageRequest.of(0, 1));
                HomeDto dto = HomeDto.builder()
                        .postID(p.getId())
                        .title(p.getTitle())
                        .star(p.getAvgRate())
                        .build();

                if (image.size() > 0) {
                    dto.setUrlImage(image.get(0).getImageUrl());
                }
                homeDtoList.add(dto);
            });

            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), homeDtoList));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> createPosting(PostRequest postRequest) {

        Host host = hostRepository.getHostsByUser_Id(SecurityUtils.getPrincipal().getId());

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = localDateTime.toLocalDate();


        Date dateStart = Date.valueOf(localDate);

        Post post = Post.builder()
                .title(postRequest.getTitle())
                .price(postRequest.getPrice())
                .host(host)
                .createDate(dateStart)
                .statusReport(1)
                .status(0)
                .build();

        Post savePost = postRepository.save(post);

        if (Objects.isNull(savePost)) {
            throw new SaveDataException("Insert post not success");
        } else {

            String[] addr = postRequest.getAddress().split(", ");

            String[] provinceName = addr[addr.length - 2].split("\\d");

            Province province = provinceRepository.getProvincesByName(" " + provinceName[0].trim());

            if (Objects.isNull(province)) {
                throw new NotFoundException("Province khong co " + provinceName[0]);
            } else {

                District district = districtRepository.getSearchDistrict(addr[addr.length - 3], province.getId());

                if (Objects.isNull(district)) {
                    throw new NotFoundException("Dictrict khong co" + addr[addr.length - 3] + province.getName() + province.getId());
                } else {
                    RoomType roomType = roomTypeRepository.findRoomTypeById(postRequest.getRoomTypeID())
                            .orElseThrow(() -> new NotFoundException("RoomType_id khong ton tai"));

                    PostDetail postDetail = PostDetail.builder()
                            .address(postRequest.getAddress())
                            .description(postRequest.getDescription())
                            .guestNumber(postRequest.getGuestNumber())
                            .numberOfBathroom(postRequest.getNumberOfBathrooms())
                            .numberOfBedrooms(postRequest.getNumberOfBedrooms())
                            .numberOfBeds(postRequest.getNumberOfBeds())
                            .district(district)
                            .post(savePost)
                            .roomType(roomType)
                            .latitude(postRequest.getLatitude())
                            .longitude(postRequest.getLongitude())
                            .build();
                    postDetailRepository.save(postDetail);


                    postRequest.getUtilityRequests().forEach(p -> {

                        Utility utility = utilityRepository.getUtilityById(p);

                        if (!Objects.isNull(utility)) {
                            PostUtility postUtility = PostUtility.builder()
                                    .utility(utility)
                                    .post(savePost)
                                    .status(1)
                                    .build();
                            postUtilityRepository.save(postUtility);
                        }
                    });

                    postRequest.getVoucherList().forEach(v -> {
                        Voucher voucher = voucherRepository.getVoucherById(v);

                        if (!Objects.isNull(voucher)) {
                            PostVoucher postVoucher = PostVoucher.builder()
                                    .startDate(dateStart)
                                    .endDate(Date.valueOf(localDate.plusDays(voucher.getDueDay())))
                                    .post(savePost)
                                    .voucher(voucher)
                                    .status(1)
                                    .build();
                            postVoucherRepository.save(postVoucher);
                        }
                    });

                    postRequest.getPostServiceRequests().forEach(s -> {
                        Services services = servicesRepository.getServicesById(s.getServiceID());

                        if (!Objects.isNull(services)) {
                            PostServices postServices = PostServices.builder()
                                    .post(savePost)
                                    .services(services)
                                    .price(s.getPrice())
                                    .status(1)
                                    .build();
                            postServiceRepository.save(postServices);
                        }
                    });

                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.toString(), new HashMap<>() {
                        {
                            put("postID", savePost.getId());
                            put("Message", "Tao posting thanh cong");
                        }
                    }));
                }
            }
        }
    }

    @Override
    public ResponseEntity<JwtResponse> getAllPostByHost() {

        Host host = hostRepository.getHostsByUser_Id(SecurityUtils.getPrincipal().getId());

        List<PostDto> postList = postRepository.getPostDTO(host.getId());

        if (Objects.isNull(postList)) {
            throw new NotFoundException("Post khong co data nao lq den host_id");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), postList));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> editPost(Long postID, PostRequest postRequest) {

        Post post = postRepository.getPostById(postID);

        if (Objects.isNull(post)) {
            throw new NotFoundException("khong co data post-id");
        } else {
            post.setTitle(postRequest.getTitle());
            post.setPrice(postRequest.getPrice());

            postRepository.save(post);

            String[] addr = postRequest.getAddress().split(", ");

            String[] provinceName = addr[addr.length - 2].split("\\d");

            Province province = provinceRepository.getProvincesByName(" " + provinceName[0].trim());

            if (Objects.isNull(province)) {
                throw new NotFoundException("Province khong co " + provinceName[0]);
            } else {

                District district = districtRepository.getSearchDistrict(addr[addr.length - 3], province.getId());

                if (Objects.isNull(district)) {
                    throw new NotFoundException("Dictrict khong co" + addr[addr.length - 3] + province.getName() + province.getId());
                } else {
                    RoomType roomType = roomTypeRepository.findRoomTypeById(postRequest.getRoomTypeID())
                            .orElseThrow(() -> new NotFoundException("RoomType_id khong ton tai"));

                    PostDetail postDetail = postDetailRepository.getPostDetailByPost_Id(post.getId());

                    postDetail.setAddress(postRequest.getAddress());
                    postDetail.setDescription(postRequest.getDescription());
                    postDetail.setGuestNumber(postRequest.getGuestNumber());
                    postDetail.setLatitude(postRequest.getLatitude());
                    postDetail.setLongitude(postRequest.getLongitude());
                    postDetail.setNumberOfBathroom(postRequest.getNumberOfBathrooms());
                    postDetail.setNumberOfBedrooms(postRequest.getNumberOfBedrooms());
                    postDetail.setNumberOfBeds(postRequest.getNumberOfBeds());
                    postDetail.setDistrict(district);
                    postDetail.setRoomType(roomType);

                    postDetailRepository.save(postDetail);

                    List<PostUtility> postUtilities = postUtilityRepository.findPostUtilitiesByPost_Id(post.getId());

                    if (!Objects.isNull(postUtilities)) {
                        postUtilities.forEach(u -> {
                            u.setStatus(2);
                            postUtilityRepository.save(u);
                        });
                    }

                    postRequest.getUtilityRequests().forEach(u -> {
                        PostUtility postUtility = postUtilityRepository.getPostUtilityByPost_IdAndUtility_Id(post.getId(), u);

                        if (Objects.isNull(postUtility)) {
                            Utility utility = utilityRepository.getUtilityById(u);

                            if (!Objects.isNull(utility)) {
                                PostUtility pu = PostUtility.builder()
                                        .utility(utility)
                                        .post(post)
                                        .status(1)
                                        .build();
                                postUtilityRepository.save(pu);
                            }
                        } else {
                            postUtility.setStatus(1);
                            postUtilityRepository.save(postUtility);
                        }
                    });

                    List<PostServices> postServicesList = postServiceRepository.getPostServicesByPost_Id(post.getId());

                    if (!Objects.isNull(postServicesList)) {
                        postServicesList.forEach(s -> {
                            s.setStatus(2);
                            postServiceRepository.save(s);
                        });
                    }

                    postRequest.getPostServiceRequests().forEach(s -> {
                        PostServices services = postServiceRepository.getPostServicesByServices_IdAndPost_Id(s.getServiceID(), post.getId());

                        if (Objects.isNull(services)) {
                            Services sv = servicesRepository.getServicesById(s.getServiceID());

                            if (!Objects.isNull(sv)) {
                                PostServices postServices = PostServices.builder()
                                        .post(post)
                                        .services(sv)
                                        .price(s.getPrice())
                                        .status(1)
                                        .build();
                                postServiceRepository.save(postServices);
                            }
                        } else {
                            services.setStatus(1);
                            services.setPrice(s.getPrice());
                            postServiceRepository.save(services);
                        }
                    });

                    List<PostVoucher> postVouchers = postVoucherRepository.getPostVoucherByPost_Id(post.getId());

                    if (!Objects.isNull(postVouchers)) {
                        postVouchers.forEach(v -> {
                            v.setStatus(2);
                            postVoucherRepository.save(v);
                        });
                    }

                    LocalDateTime localDateTime = LocalDateTime.now();
                    LocalDate localDate = localDateTime.toLocalDate();


                    Date dateStart = Date.valueOf(localDate);

                    postRequest.getVoucherList().forEach(pv -> {

                        PostVoucher v = postVoucherRepository.getPostVoucherByPost_IdAndVoucher_Id(post.getId(), pv);

                        if (Objects.isNull(v)) {
                            Voucher voucher = voucherRepository.getVoucherById(pv);

                            if (!Objects.isNull(voucher)) {
                                PostVoucher postVoucher = PostVoucher.builder()
                                        .startDate(dateStart)
                                        .endDate(Date.valueOf(localDate.plusDays(voucher.getDueDay())))
                                        .post(post)
                                        .voucher(voucher)
                                        .status(1)
                                        .build();
                                postVoucherRepository.save(postVoucher);
                            }
                        } else {
                            v.setStatus(1);
                            postVoucherRepository.save(v);
                        }
                    });
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.toString(), new HashMap<>() {
                {
                    put("postID", post.getId());
                    put("Message", "Edit posting thanh cong");
                }
            }));
        }
    }

    @Override
    public ResponseEntity<MessageResponse> updateStatus(Long postID, int status) {

        Post post = postRepository.getPostById(postID);

        if (Objects.isNull(post)) {
            throw new NotFoundException("khong co data post-id");
        } else {
            post.setStatus(status);
            postRepository.save(post);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(HttpStatus.OK.value(), "update status thanh cong"));
        }
    }

}
