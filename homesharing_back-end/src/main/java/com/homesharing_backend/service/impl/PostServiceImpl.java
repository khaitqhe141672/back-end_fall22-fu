package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.entity.Post;
import com.homesharing_backend.data.repository.PostRepository;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public ResponseEntity<JwtResponse> getInterestingPlaceByPost() {

        List<Post> postList = postRepository.getPostTop();

        if(postList.isEmpty()){
            throw new NotFoundException("không có dữ liệu");
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), postList));
        }
    }
}
