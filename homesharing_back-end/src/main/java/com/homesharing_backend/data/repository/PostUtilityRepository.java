package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.dto.PostUtilityDto;
import com.homesharing_backend.data.entity.PostUtility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostUtilityRepository extends JpaRepository<PostUtility, Long> {

    Optional<PostUtility> getPostUtilitiesByPost_Id(Long postID);

    List<PostUtility> findPostUtilitiesByPost_Id(Long postID);

    PostUtility getPostUtilityById(Long id);

    PostUtility getPostUtilityByPost_IdAndUtility_Id(Long postID, Long utilityID);

    List<PostUtility> getPostUtilityByPost_IdAndStatus(Long postID, int status);

    @Query(value = "SELECT new com.homesharing_backend.data.dto.PostUtilityDto(u.id, u.utility.name, u.utility.id, u.status) FROM PostUtility u " +
            "WHERE u.post.id= :postID AND u.status = :status")
    List<PostUtilityDto> getAllPostVoucherDTOByPostIDAndStatus(@Param("postID") Long postID, @Param("status") int status);
}
