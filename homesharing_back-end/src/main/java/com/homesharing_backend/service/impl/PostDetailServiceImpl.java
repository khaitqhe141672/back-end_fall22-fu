package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.*;
import com.homesharing_backend.data.entity.*;
import com.homesharing_backend.data.repository.*;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.service.PostDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PostDetailServiceImpl implements PostDetailService {

    @Autowired
    private PostDetailRepository postDetailRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostImageRepository postImageRepository;

    @Autowired
    private PostUtilityRepository postUtilityRepository;

    @Autowired
    private PostServiceRepository postServiceRepository;

    /*
        còn voucher, rate bổ sung sau
    */
    @Override
    public ResponseEntity<JwtResponse> getPostDetailByPostID(Long postID) {

        if (!postRepository.existsPostById(postID)) {
            throw new NotFoundException("Post_ID không tôn tại!");
        } else {
            PostDetail postDetail = postDetailRepository.findPostDetailByPost_Id(postID)
                    .orElseThrow(() -> new NotFoundException("Không có post_id này trong post_detail"));
            DistrictDto districtDto = DistrictDto.builder()
                    .districtName(postDetail.getDistrict().getName())
                    .provinceName(postDetail.getDistrict().getProvince().getName())
                    .build();

            List<PostImage> postImages = postImageRepository.findPostImageByPost_Id(postID);

            List<PostImageDto> postImageDtoList = new ArrayList<>();
            postImages.forEach(postImage -> {
                postImageDtoList.add(new PostImageDto(postImage.getId(), postImage.getImageUrl()));
            });

            List<PostUtility> postUtilities = postUtilityRepository.findPostUtilitiesByPost_Id(postID);

            List<PostUtilityDto> utilityDtoList = new ArrayList<>();
            postUtilities.forEach(postUtility -> {
                utilityDtoList.add(new PostUtilityDto(postUtility.getId(), postUtility.getUtility().getIcon(),
                        postUtility.getUtility().getName(),
                        postUtility.getUtility().getId(), postUtility.getStatus()));
            });

            PostTopRateDto postTopRateDto = postRepository.getPostDetailByPostID(postID)
                    .orElseThrow(() -> new NotFoundException("khong co data lien quan den post_id cua rate"));

            List<PostServiceDto> serviceDtoList = new ArrayList<>();

            List<PostServices> postServices = postServiceRepository.getPostServicesByPost_Id(postID);
            if (!Objects.isNull(postServices)) {
                postServices.forEach(s -> {
                    PostServiceDto dto = PostServiceDto.builder()
                            .iconService(s.getServices().getIcon())
                            .postServiceID(s.getId())
                            .serviceID(s.getServices().getId())
                            .nameService(s.getServices().getName())
                            .status(s.getStatus())
                            .build();
                    serviceDtoList.add(dto);
                });
            }

            PostDetailDto dto = PostDetailDto.builder()
                    .postDetailID(postDetail.getId())
                    .postID(postID)
                    .title(postDetail.getPost().getTitle())
                    .price(postDetail.getPost().getPrice())
                    .createDate(postDetail.getPost().getCreateDate())
                    .hostName(postDetail.getPost().getHost().getUser().getUserDetail().getFullName())
                    .mobileHost(postDetail.getPost().getHost().getUser().getUserDetail().getMobile())
                    .imageUrlHost(postDetail.getPost().getHost().getUser().getUserDetail().getAvatarUrl())
                    .address(postDetail.getAddress())
                    .description(postDetail.getDescription())
                    .guestNumber(postDetail.getGuestNumber())
                    .numberOfBathrooms(postDetail.getNumberOfBathroom())
                    .numberOfBedrooms(postDetail.getNumberOfBedrooms())
                    .numberOfBeds(postDetail.getNumberOfBeds())
                    .serviceDtoList(serviceDtoList)
                    .roomTypeName(postDetail.getRoomType().getName())
                    .imageDtoList(postImageDtoList)
                    .postUtilityDtoList(utilityDtoList)
                    .districtDto(districtDto)
                    .avgRate(postTopRateDto.getAvgRate())
                    .status(postDetail.getPost().getStatus())
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), dto));
        }
    }

    @Override
    public ResponseEntity<JwtResponse> getRateByPostID(Long postID) {

        if (postRepository.existsPostById(postID)) {
            throw new NotFoundException("Post_ID không tôn tại!");
        } else {
            PostTopRateDto postTopRateDto = postRepository.getPostDetailByPostID(postID)
                    .orElseThrow(() -> new NotFoundException("khong co data lien quan den post_id cua rate"));
            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), postTopRateDto));
        }
    }
}
