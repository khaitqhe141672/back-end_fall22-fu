package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage,Long> {

    Optional<PostImage> getPostImageByPost_Id(Long postID);

    List<PostImage> findPostImageByPost_Id(Long postID);
}
