package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.repository.PostImageRepository;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.service.PostImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PostImageServiceImpl implements PostImageService {

    @Autowired
    private PostImageRepository postImageRepository;

    @Override
    public ResponseEntity<ResponseObject> getAllPostImageByPostID(Long postID) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> insertPostImage(List<MultipartFile> multipartFile) {



        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> editPostImageByPostImageID(Long postID, List<MultipartFile> multipartFile) {
        return null;
    }
}
