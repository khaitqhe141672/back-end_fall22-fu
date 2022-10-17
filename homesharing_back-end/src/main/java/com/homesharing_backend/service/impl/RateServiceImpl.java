package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.RateDto;
import com.homesharing_backend.data.entity.Rate;
import com.homesharing_backend.data.repository.PostRepository;
import com.homesharing_backend.data.repository.RateRepository;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class RateServiceImpl implements RateService {

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public ResponseEntity<JwtResponse> getAllRate(Long postID) {

        if (postRepository.existsPostById(postID)) {
            throw new NotFoundException("post_id khong ton tai");
        } else {
            List<Rate> rates = (List<Rate>) rateRepository.findAllByBookingDetail_Post_Id(postID)
                    .orElseThrow(() -> new NotFoundException("Khong co rate nao lien quan den post_id nay"));


            List<RateDto> rateDtos = new ArrayList<>();

            rates.forEach(r -> {
                RateDto dto = RateDto.builder()
                        .rateID(r.getId())
                        .postID(r.getBookingDetail().getPost().getId())
                        .customerID(r.getBookingDetail().getBooking().getCustomer().getId())
                        .username(r.getBookingDetail().getBooking().getCustomer().getUser().getUsername())
                        .urlImage(r.getBookingDetail().getBooking().getCustomer().getUser().getUserDetail().getAvatarUrl())
                        .comment(r.getComment())
                        .point(r.getPoint())
                        .dateRate(r.getDateRate())
                        .status(r.getStatus())
                        .build();
                rateDtos.add(dto);
            });

            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), rateDtos));
        }
    }
}
