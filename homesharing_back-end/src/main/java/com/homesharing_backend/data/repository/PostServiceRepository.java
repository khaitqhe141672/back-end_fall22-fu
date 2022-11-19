package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.PostServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostServiceRepository extends JpaRepository<PostServices, Long> {

    List<PostServices> getPostServicesByPost_Id(Long postID);

    PostServices getPostServicesByIdAndPost_Id(Long id, Long postID);

    PostServices getPostServicesByServices_IdAndPost_Id(Long serviceID, Long postID);
}
