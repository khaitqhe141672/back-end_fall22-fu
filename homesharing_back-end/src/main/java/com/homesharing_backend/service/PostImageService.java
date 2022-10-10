package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface PostImageService {

    public ResponseEntity<ResponseObject> getAllPostImageByPostID(Long postID);

    public ResponseEntity<ResponseObject> insertPostImage(List<MultipartFile> multipartFile);

    public ResponseEntity<ResponseObject> editPostImageByPostImageID(Long postID, List<MultipartFile> multipartFile);
}
