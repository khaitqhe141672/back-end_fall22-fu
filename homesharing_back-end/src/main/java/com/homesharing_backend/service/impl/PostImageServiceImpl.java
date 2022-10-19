package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.entity.PostImage;
import com.homesharing_backend.data.repository.PostImageRepository;
import com.homesharing_backend.data.repository.PostRepository;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.service.PostImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PostImageServiceImpl implements PostImageService {

    @Autowired
    private PostImageRepository postImageRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AWSService awsService;

    @Override
    public ResponseEntity<ResponseObject> getAllPostImageByPostID(Long postID) {
        return null;
    }

    @Override
    public ResponseEntity<MessageResponse> insertPostImage(List<MultipartFile> multipartFile, Long postID) {

        if (!postRepository.existsPostById(postID)) {
            throw new NotFoundException("Post_id khong ton tai");
        } else {
            if (multipartFile.isEmpty()) {
                throw new NotFoundException("File trong");
            } else {
                multipartFile.forEach(file -> {
                    String fileName = awsService.upload(file);
                    PostImage postImage = PostImage.builder()
                            .post(postRepository.getPostById(postID))
                            .imageUrl(fileName)
                            .build();
                    postImageRepository.save(postImage);
                });
            }
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(HttpStatus.OK.value(), "Tao post thanh cong"));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> editPostImageByPostImageID(Long postID, List<MultipartFile> multipartFile) {
        return null;
    }
}
