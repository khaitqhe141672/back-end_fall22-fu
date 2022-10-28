package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.HomeDto;
import com.homesharing_backend.data.dto.PostDto;
import com.homesharing_backend.data.dto.PostTopRateDto;
import com.homesharing_backend.data.entity.*;
import com.homesharing_backend.data.repository.*;
import com.homesharing_backend.exception.EmptyDataException;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.exception.SaveDataException;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.request.PostRequest;
import com.homesharing_backend.service.PostService;
import com.homesharing_backend.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public ResponseEntity<JwtResponse> getInterestingPlaceByPost() {

        List<Post> postList = postRepository.getPostTop();

        if (postList.isEmpty()) {
            throw new NotFoundException("không có dữ liệu");
        } else {

            List<HomeDto> homeDtoList = new ArrayList<>();

            postList.forEach(p -> {
                List<PostImage> image = postImageRepository.findPostImageByPost_Id(p.getId());
                HomeDto dto = HomeDto.builder()
                        .postID(p.getId())
                        .urlImage(image.get(0).getImageUrl())
                        .title(p.getTitle())
                        .build();
                homeDtoList.add(dto);
            });

            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), homeDtoList));
        }
    }

    @Override
    public ResponseEntity<JwtResponse> getTopPostByRate() {

        List<PostTopRateDto> postTopRateDtos = postRepository.getTopPostByRate();

        if (postTopRateDtos.isEmpty()) {
            throw new NotFoundException("không có dữ liệu");
        } else {
            List<HomeDto> homeDtoList = new ArrayList<>();

            for (int i = 0; i <= 7; i++) {
                List<PostImage> image = postImageRepository.findPostImageByPost_Id(postTopRateDtos.get(i).getId());
                HomeDto dto = HomeDto.builder()
                        .postID(postTopRateDtos.get(i).getId())
                        .urlImage(image.get(0).getImageUrl())
                        .title(postTopRateDtos.get(i).getTitle())
                        .star(postTopRateDtos.get(i).getAvgRate())
                        .build();
                homeDtoList.add(dto);
            }

            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), homeDtoList));
        }
    }

    @Override
    public ResponseEntity<MessageResponse> createPosting(PostRequest postRequest) {

        Host host = hostRepository.getHostsByUser_Id(SecurityUtils.getPrincipal().getId());

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = localDateTime.toLocalDate();


        Date dateStart = Date.valueOf(localDate);

        Post post = Post.builder()
                .title(postRequest.getTitle())
                .price(postRequest.getPrice())
                .host(host)
                .createDate(dateStart)
                .status(0)
                .build();

        Post savePost = postRepository.save(post);

        if (Objects.isNull(savePost)) {
            throw new SaveDataException("Insert post not success");
        } else {

            District district = districtRepository.findDistrictById(postRequest.getDistrictID())
                    .orElseThrow(() -> new NotFoundException("District_id khong ton tai"));

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

                Utility utility = utilityRepository.findUtilityById(p.getUtilityID())
                        .orElseThrow(() -> new NotFoundException("Utility_id khong ton tai"));

                PostUtility postUtility = PostUtility.builder()
                        .utility(utility)
                        .post(savePost)
                        .price(p.getPrice())
                        .status(1)
                        .build();
                postUtilityRepository.save(postUtility);
            });

            Voucher voucher = voucherRepository.findVoucherById(postRequest.getVoucherID())
                    .orElseThrow(() -> new NotFoundException("Voucher_id khong ton tai"));

            PostVoucher postVoucher = PostVoucher.builder()
                    .startDate(dateStart)
                    .endDate(Date.valueOf(localDate.plusDays(voucher.getDueDay())))
                    .post(savePost)
                    .voucher(voucher)
                    .status(1)
                    .build();
            postVoucherRepository.save(postVoucher);

            PaymentPackage paymentPackage = paymentPackageRepository.findPaymentPackageById(postRequest.getPaymentPackageID())
                    .orElseThrow(() -> new NotFoundException("PaymentPackage_id khong ton tai"));
            PostPayment postPayment = PostPayment.builder()
                    .post(savePost)
                    .paymentPackage(paymentPackage)
                    .startDate(dateStart)
                    .endDate(Date.valueOf(localDate.plusMonths(voucher.getDueDay())))
                    .status(0)
                    .build();
            postPaymentRepository.save(postPayment);

            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(HttpStatus.OK.value(), "Tao post thanh cong"));
        }
    }

    @Override
    public ResponseEntity<JwtResponse> getAllPostByHost() {

//        Host host = hostRepository.getHostsByUser_Id(SecurityUtils.getPrincipal().getId());
//
//        List<PostDto> postList = postRepository.listAllPostByHost(host.getId());
//
//        if (Objects.isNull(postList)) {
//            throw new NotFoundException("Post khong co data nao lq den host_id");
//        } else {
//            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), postList));
//        }
        return null;
    }

}
