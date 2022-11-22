package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.PostVoucherDto;
import com.homesharing_backend.data.entity.Post;
import com.homesharing_backend.data.entity.PostVoucher;
import com.homesharing_backend.data.entity.Voucher;
import com.homesharing_backend.data.repository.PostRepository;
import com.homesharing_backend.data.repository.PostVoucherRepository;
import com.homesharing_backend.data.repository.VoucherRepository;
import com.homesharing_backend.exception.EmptyDataException;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.request.PostVoucherRequest;
import com.homesharing_backend.service.PostVoucherService;
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
public class PostVoucherServiceImpl implements PostVoucherService {

    @Autowired
    private PostVoucherRepository postVoucherRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private VoucherRepository voucherRepository;

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

    @Override
    public ResponseEntity<MessageResponse> insertPostVoucher(Long postID, PostVoucherRequest postVoucherRequest) {

        Post post = postRepository.getPostById(postID);

        if (Objects.isNull(post)) {
            throw new NotFoundException("Post-id khong ton tai");
        } else {

            LocalDateTime localDateTime = LocalDateTime.now();
            LocalDate localDate = localDateTime.toLocalDate();

            Date dateNow = Date.valueOf(localDate);

            postVoucherRequest.getVoucherIDList().forEach(v -> {

                Voucher voucher = voucherRepository.getVoucherById(v);

                PostVoucher postVoucher = postVoucherRepository.getPostVoucherByPost_IdAndVoucher_Id(post.getId(), voucher.getId());

                if (Objects.isNull(postVoucher)) {
                    PostVoucher pv = PostVoucher.builder()
                            .startDate(dateNow)
                            .endDate(Date.valueOf(localDate.plusDays(voucher.getDueDay())))
                            .status(1)
                            .post(post)
                            .build();
                    postVoucherRepository.save(pv);
                } else {
                    postVoucher.setStartDate(dateNow);
                    postVoucher.setStatus(1);
                    postVoucher.setEndDate(Date.valueOf(localDate.plusDays(voucher.getDueDay())));
                    postVoucherRepository.save(postVoucher);
                }

            });
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(HttpStatus.OK.value(), "Add voucher thanh cong"));
        }
    }
}
