package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.PostImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    List<PostImage> findPostImageByPost_Id(Long postID);

    PostImage getPostImageById(Long id);

    List<PostImage> getPostImageByPost_Id(Long postID, PageRequest pageRequest);
}
