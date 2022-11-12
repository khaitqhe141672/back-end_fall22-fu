package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.PostDto;
import com.homesharing_backend.data.dto.PostView;
import com.homesharing_backend.data.entity.Host;
import com.homesharing_backend.data.entity.Post;
import com.homesharing_backend.data.entity.PostDetail;
import com.homesharing_backend.data.entity.PostPayment;
import com.homesharing_backend.data.repository.PostDetailRepository;
import com.homesharing_backend.data.repository.PostPaymentRepository;
import com.homesharing_backend.data.repository.PostRepository;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.service.ManagePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class ManagePostServiceImpl implements ManagePostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostDetailRepository postDetailRepository;

    @Autowired
    private PostPaymentRepository postPaymentRepository;

    @Override
    public ResponseEntity<ResponseObject> getAllPostByAdmin(int indexPage) {

        int size = 10;
        int page = indexPage * size - size;

        Page<Post> postList = postRepository.findAll(PageRequest.of(page, size));
        List<PostView> postDtoList = new ArrayList<>();

        postList.forEach(p -> {
            PostDetail postDetail = postDetailRepository.getPostDetailByPost_Id(p.getId());
            PostPayment postPayment = postPaymentRepository.getTimePost(p.getId());

            PostView dto = PostView.builder()
                    .postID(p.getId())
                    .title(p.getTitle())
                    .statusPost(p.getStatus())
                    .build();

            if (!Objects.isNull(postPayment)) {
                dto.setEndDate(postPayment.getEndDate());
                dto.setStartDate(postPayment.getStartDate());
                dto.setStatusPostPayment(postPayment.getStatus());
            } else {
                dto.setStatusPostPayment(0);
            }

            postDtoList.add(dto);
        });
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
            {
                put("List-Post", postDtoList);
                put("SizePage", postList.getTotalPages());
            }
        }));
    }
}
