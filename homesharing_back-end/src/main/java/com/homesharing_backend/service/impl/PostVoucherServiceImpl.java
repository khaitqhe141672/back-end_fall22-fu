package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.PostVoucherDto;
import com.homesharing_backend.data.entity.Post;
import com.homesharing_backend.data.entity.PostVoucher;
import com.homesharing_backend.data.repository.PostRepository;
import com.homesharing_backend.data.repository.PostVoucherRepository;
import com.homesharing_backend.exception.EmptyDataException;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.service.PostVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PostVoucherServiceImpl implements PostVoucherService {

    @Autowired
    private PostVoucherRepository postVoucherRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public ResponseEntity<JwtResponse> getPostVoucherByPostID(Long postID) {

        Post post = postRepository.getPostById(postID);

        if (Objects.isNull(post)) {
            throw new NotFoundException("Post-id khong ton tai");
        } else {
            List<PostVoucherDto> postVoucherDtoList = new ArrayList<>();

            List<PostVoucher> postVouchers = postVoucherRepository.getPostVoucherByPost_IdAndStatus(post.getId(), 1);

            postVouchers.forEach(v -> {
                PostVoucherDto dto = PostVoucherDto.builder()
                        .postVoucherID(v.getId())
                        .voucherID(v.getVoucher().getId())
                        .code(v.getVoucher().getCode())
                        .description(v.getVoucher().getDescription())
                        .dueDay(v.getVoucher().getDueDay())
                        .percent(v.getVoucher().getPercent())
                        .startDate(v.getStartDate())
                        .endDate(v.getEndDate())
                        .status(v.getStatus())
                        .build();

                postVoucherDtoList.add(dto);
            });

            if (Objects.isNull(postVoucherDtoList)) {
                throw new EmptyDataException("khong co voucher theo post");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), postVoucherDtoList));
            }
        }
    }
}
