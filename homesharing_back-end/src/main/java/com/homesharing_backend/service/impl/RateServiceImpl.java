package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.RateDto;
import com.homesharing_backend.data.entity.BookingDetail;
import com.homesharing_backend.data.entity.Rate;
import com.homesharing_backend.data.repository.*;
import com.homesharing_backend.exception.EmptyDataException;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.exception.SaveDataException;
import com.homesharing_backend.exception.UpdateDataException;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.RateRequest;
import com.homesharing_backend.service.RateService;
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
public class RateServiceImpl implements RateService {

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private BookingDetailRepository bookingDetailRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private LikesDislikesRepository likesDislikesRepository;

    @Override
    public ResponseEntity<ResponseObject> getAllRate(Long postID, int indexPage) {

        int size = 5;
        int page = indexPage - 1;

        if (!postRepository.existsPostById(postID)) {
            throw new NotFoundException("post_id khong ton tai trong rate");
        } else {
            Page<Rate> rates = rateRepository.findAllByBookingDetail_Post_IdAndStatus(postID, 1, PageRequest.of(page, size));

            List<RateDto> rateDtos = new ArrayList<>();

            rates.forEach(r -> {

                int countLike = likesDislikesRepository.countLikesDislikesByRate_IdAndType(r.getId(), 1);
                int countDislike = likesDislikesRepository.countLikesDislikesByRate_IdAndType(r.getId(), 2);

                RateDto dto = RateDto.builder()
                        .rateID(r.getId())
                        .postID(r.getBookingDetail().getPost().getId())
                        .customerID(r.getBookingDetail().getBooking().getCustomer().getId())
                        .username(r.getBookingDetail().getBooking().getCustomer().getUser().getUsername())
                        .urlImage(r.getBookingDetail().getBooking().getCustomer().getUser().getUserDetail().getAvatarUrl())
                        .comment(r.getComment())
                        .point(r.getPoint())
                        .dateRate(r.getDateRate())
                        .countDislike(countDislike)
                        .countLike(countLike)
                        .status(r.getStatus())
                        .build();
                rateDtos.add(dto);
            });

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("rate by post", new HashMap<>() {
                {
                    put("listRate", rateDtos);
                    put("sizePage", rates.getTotalPages());
                }
            }));
        }
    }

    @Override
    public ResponseEntity<MessageResponse> createRateByCustomer(RateRequest rateRequest, Long bookingDetailID) {

        BookingDetail bookingDetail = bookingDetailRepository.getBookingDetailById(bookingDetailID);

        if (Objects.isNull(bookingDetail)) {
            throw new NotFoundException("bookingDetail_id khong ton tai trong rate");
        } else {
            LocalDateTime localDateTime = LocalDateTime.now();
            LocalDate localDate = localDateTime.toLocalDate();

            Date createDate = Date.valueOf(localDate);

            if (Objects.isNull(rateRequest.getComment()) || Objects.isNull(rateRequest.getPoint())) {
                throw new NotFoundException("Comment or Point empty");
            } else {
                Rate rate = Rate.builder()
                        .comment(rateRequest.getComment())
                        .point(rateRequest.getPoint())
                        .dateRate(createDate)
                        .bookingDetail(bookingDetail)
                        .status(1)
                        .build();

                Rate saveRate = rateRepository.save(rate);
                if (Objects.isNull(saveRate)) {
                    throw new SaveDataException("rate khong thanh cong");
                } else {
                    return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(200, "rate thanh cong"));
                }
            }
        }
    }

    /* status = 2 la da chinh sua roi*/
    @Override
    public ResponseEntity<MessageResponse> editRateByCustomer(RateRequest rateRequest, Long rateID) {

        Rate rate = rateRepository.getRateById(rateID);

        if (Objects.isNull(rate)) {
            throw new NotFoundException("Rate_id khong ton tai trong rate");
        } else {

            LocalDateTime localDateTime = LocalDateTime.now();
            LocalDate localDate = localDateTime.toLocalDate();

            Date editDate = Date.valueOf(localDate);

            rate.setComment(rateRequest.getComment());
            rate.setPoint(rateRequest.getPoint());
            rate.setDateRate(editDate);
            rate.setStatus(2);

            Rate updateRate = rateRepository.save(rate);

            if (Objects.isNull(updateRate)) {
                throw new UpdateDataException("rate edit khong thanh cong");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(200, "rate edit thanh cong"));
            }
        }
    }

    /* status = 3 la xoa rate*/
    @Override
    public ResponseEntity<MessageResponse> deleteRateByCustomer(Long rateID) {

        Rate rate = rateRepository.getRateById(rateID);

        if (Objects.isNull(rate)) {
            throw new NotFoundException("Rate_id khong ton tai trong rate");
        } else {
            rate.setStatus(3);

            Rate deleteRate = rateRepository.save(rate);

            if (Objects.isNull(deleteRate)) {
                throw new UpdateDataException("delete rate khong thanh cong");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(200, "delete rate thanh cong"));
            }
        }
    }
}
