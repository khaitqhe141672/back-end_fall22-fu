package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.PostDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostDetailRepository extends JpaRepository<PostDetail, Long> {

    PostDetail getPostDetailByPost_Id(Long postID);

    Optional<PostDetail> findPostDetailById(Long postID);
}
