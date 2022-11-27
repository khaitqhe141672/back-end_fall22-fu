package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.dto.PostServiceDto;
import com.homesharing_backend.data.entity.PostServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostServiceRepository extends JpaRepository<PostServices, Long> {

    List<PostServices> getPostServicesByPost_Id(Long postID);

    PostServices getPostServicesByIdAndPost_Id(Long id, Long postID);

    PostServices getPostServicesByServices_IdAndPost_Id(Long serviceID, Long postID);

    List<PostServices> getPostServicesByPost_IdAndStatus(Long postID, int status);

    @Query(value = "SELECT new com.homesharing_backend.data.dto.PostServiceDto(s.id, s.services.name, s.services.id, s.status) FROM PostServices s " +
            "WHERE s.post.id= :postID AND s.status= :status")
    List<PostServiceDto> getAllPostServiceByPostIDAndStatus(@Param("postID") Long postID, @Param("status") int status);
}
