package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.FavouritePostDto;
import com.homesharing_backend.data.dto.FollowHostDto;
import com.homesharing_backend.data.entity.*;
import com.homesharing_backend.data.repository.*;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.exception.SaveDataException;
import com.homesharing_backend.exception.UpdateDataException;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.service.FollowAndFavouriteService;
import com.homesharing_backend.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class FollowAndFavouriteServiceImpl implements FollowAndFavouriteService {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private FollowHostRepository followHostRepository;

    @Autowired
    private FavouritePostRepository favouritePostRepository;

    /*status = 1 theo doi host*/
    @Override
    public ResponseEntity<ResponseObject> followHostByCustomer(Long hostID) {

        Customer customer = customerRepository.getCustomerByUser_Id(SecurityUtils.getPrincipal().getId());

        Host host = hostRepository.getHostsById(hostID);

        if (Objects.isNull(host)) {
            throw new NotFoundException("Host_id khong ton tai");
        } else {
            FollowHost followHost = FollowHost.builder()
                    .host(host)
                    .customer(customer)
                    .status(1)
                    .build();
            FollowHost save = followHostRepository.save(followHost);

            if (Objects.isNull(save)) {
                throw new SaveDataException("Follow host khong thanh cong");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Follow host success", new HashMap<>() {
                    {
                        put("FollowerHostID", save.getId());
                        put("customerFollow", save.getCustomer().getId());
                        put("status", save.getStatus());
                    }
                }));
            }
        }
    }

    /*status = 2 bo theo doi host*/
    @Override
    public ResponseEntity<ResponseObject> editFollowHostByCustomer(Long followHostID) {

        FollowHost followHost = followHostRepository.getFollowHostById(followHostID);

        Customer customer = customerRepository.getCustomerByUser_Id(SecurityUtils.getPrincipal().getId());

        if (Objects.isNull(followHost)) {
            throw new NotFoundException("followHost_id khong ton tai");
        } else {

            if (customer.getId() != followHost.getCustomer().getId()) {
                throw new NotFoundException("Customer_id khong chua follow host");
            } else {
                followHost.setStatus(2);
                FollowHost edit = followHostRepository.save(followHost);

                if (Objects.isNull(edit)) {
                    throw new UpdateDataException("edit Follow host khong thanh cong");
                } else {
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("edit Follow host success", new HashMap<>() {
                        {
                            put("FollowerHostID", edit.getId());
                            put("customerFollow", edit.getCustomer().getId());
                            put("status", edit.getStatus());
                        }
                    }));
                }
            }
        }
    }

    @Override
    public ResponseEntity<JwtResponse> getCountFollowHostByHostID(Long hostID) {

        Host host = hostRepository.getHostsById(hostID);

        if (Objects.isNull(host)) {
            throw new NotFoundException("host_id khong ton tai");
        } else {
            int countFollowHost = followHostRepository.countFollowHostByHost_Id(hostID);

            FollowHostDto dto = FollowHostDto.builder()
                    .countFollowHost(countFollowHost)
                    .hostID(host.getId())
                    .status(1)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), dto));
        }
    }

    /*status = 1 theo doi post*/
    @Override
    public ResponseEntity<ResponseObject> createFavouritePostByCustomer(Long postID) {

        Post post = postRepository.getPostById(postID);

        Customer customer = customerRepository.getCustomerByUser_Id(SecurityUtils.getPrincipal().getId());

        if (Objects.isNull(post)) {
            throw new NotFoundException("post_id khong ton tai");
        } else {
            FavouritePost favouritePost = FavouritePost.builder()
                    .customer(customer)
                    .post(post)
                    .status(1)
                    .build();

            FavouritePost save = favouritePostRepository.save(favouritePost);

            if (Objects.isNull(save)) {
                throw new SaveDataException("Favourite post khong thanh cong");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Favourite post success", new HashMap<>() {
                    {
                        put("FavouriteID", save.getId());
                        put("customerFavourite", save.getCustomer().getId());
                        put("status", save.getStatus());
                    }
                }));
            }
        }
    }

    /*status = 2 bo theo doi post*/
    @Override
    public ResponseEntity<ResponseObject> editFavouritePostByCustomer(Long favouritePostID) {

        Customer customer = customerRepository.getCustomerByUser_Id(SecurityUtils.getPrincipal().getId());
        FavouritePost favouritePost = favouritePostRepository.getFavouritePostById(favouritePostID);

        if (Objects.isNull(favouritePost)) {
            throw new NotFoundException("favouritePost_id khong ton tai");
        } else {
            if (customer.getId() != favouritePost.getCustomer().getId()) {
                throw new NotFoundException("Customer_id khong chua follow host");
            } else {
                favouritePost.setStatus(2);

                FavouritePost edit = favouritePostRepository.save(favouritePost);

                if (Objects.isNull(edit)) {
                    throw new UpdateDataException("edit FfavouritePost khong thanh cong");
                } else {
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("edit Favourite post success", new HashMap<>() {
                        {
                            put("FavouriteID", edit.getId());
                            put("customerFavourite", edit.getCustomer().getId());
                            put("status", edit.getStatus());
                        }
                    }));
                }
            }
        }
    }

    @Override
    public ResponseEntity<JwtResponse> getCountFavouritePostByPostID(Long postID) {

        Post post = postRepository.getPostById(postID);

        if (Objects.isNull(post)) {
            throw new NotFoundException("post_id khong ton tai");
        } else {
            int countFavouritePost = favouritePostRepository.countFavouritePostByPost_Id(post.getId());

            FavouritePostDto dto = FavouritePostDto.builder()
                    .countFavouritePost(countFavouritePost)
                    .postID(post.getId())
                    .status(1)
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), dto));
        }
    }
}
