package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.entity.Customer;
import com.homesharing_backend.data.entity.LikesDislikes;
import com.homesharing_backend.data.entity.Rate;
import com.homesharing_backend.data.repository.CustomerRepository;
import com.homesharing_backend.data.repository.LikesDislikesRepository;
import com.homesharing_backend.data.repository.RateRepository;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.exception.SaveDataException;
import com.homesharing_backend.exception.UpdateDataException;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.service.LikesDislikesService;
import com.homesharing_backend.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class LikesDislikesServiceImpl implements LikesDislikesService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private LikesDislikesRepository likesDislikesRepository;


    /* type = 1 like; type = 2 dislike*/
    @Override
    public ResponseEntity<ResponseObject> createLikeOrDislikeRateByCustomer(Long rateID, int type) {

        Customer customer = customerRepository.getCustomerByUser_Id(SecurityUtils.getPrincipal().getId());

        Rate rate = rateRepository.getRateById(rateID);

        if (Objects.isNull(rate)) {
            throw new NotFoundException("Rate_id khong ton tai");
        } else {

            LikesDislikes likesDislikes = likesDislikesRepository.getLikesDislikesByCustomer_Id(rate.getId());

            if (Objects.isNull(likesDislikes)) {
                LikesDislikes lk = LikesDislikes.builder()
                        .rate(rate)
                        .customer(customer)
                        .status(1)
                        .build();

                if (type == 1) {
                    lk.setType(1);
                } else {
                    lk.setType(2);
                }

                LikesDislikes save = likesDislikesRepository.save(likesDislikes);

                if (Objects.isNull(save)) {
                    throw new SaveDataException("Like or dislike not success");
                } else {
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Like or dislike success", new HashMap<>() {
                        {
                            put("likeDislikeID", save.getId());
                            put("type", save.getType());
                            put("status", save.getStatus());
                        }
                    }));
                }
            } else {
                if (type == 1) {
                    likesDislikes.setType(2);
                } else {
                    likesDislikes.setType(1);
                }
                LikesDislikes save = likesDislikesRepository.save(likesDislikes);
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Like or dislike success", new HashMap<>() {
                    {
                        put("likeDislikeID", save.getId());
                        put("type", save.getType());
                        put("status", save.getStatus());
                    }
                }));
            }
        }
    }

    @Override
    public ResponseEntity<ResponseObject> editLikeOrDislikeRateByCustomer(Long likeOrDislikeID, int type) {

        LikesDislikes likesDislikes = likesDislikesRepository.getLikesDislikesById(likeOrDislikeID);

        Customer customer = customerRepository.getCustomerByUser_Id(SecurityUtils.getPrincipal().getId());

        if (Objects.isNull(likesDislikes)) {
            throw new NotFoundException("LikeOrDislike-ID khong ton tai");
        } else {
            if (customer.getId() != likesDislikes.getCustomer().getId()) {
                throw new NotFoundException("Customer khong phai nguoi da like or dislike");
            } else {
                likesDislikes.setType(type);
                LikesDislikes update = likesDislikesRepository.save(likesDislikes);

                if (Objects.isNull(update)) {
                    throw new UpdateDataException("Update not success");
                } else {
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Update Like or dislike success", new HashMap<>() {
                        {
                            put("likeDislikeID", update.getId());
                            put("type", update.getType());
                            put("status", update.getStatus());
                        }
                    }));
                }
            }
        }
    }

    @Override
    public ResponseEntity<JwtResponse> countLikeOrDislikeRateByCustomer(Long rateID) {

        return null;
    }
}
