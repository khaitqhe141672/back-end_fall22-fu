package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.dto.ReportPostDto;
import com.homesharing_backend.data.entity.ReportPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportPostRepository extends JpaRepository<ReportPost, Long> {

    ReportPost getReportPostById(Long id);

    List<ReportPost> getReportPostByPost_Id(Long postID);

    Page<ReportPost> findReportPostByPost_Id(Long postID, PageRequest pageRequest);

    int countReportPostByPost_Id(Long postID);

    @Query(value = "SELECT DISTINCT new com.homesharing_backend.data.dto.ReportPostDto(p.id, p.title, p.price, pi.imageUrl," +
            " h.user.username, h.user.userDetail.avatarUrl, h.typeAccount, p.status, rp.status) FROM ReportPost rp " +
            "LEFT JOIN Post p ON rp.post.id = p.id " +
            "LEFT JOIN PostImage pi ON p.id = pi.post.id " +
            "LEFT JOIN Host h ON p.host.id = h.id " +
            "GROUP BY rp.id")
    Page<ReportPostDto> listAllReportPostByHost(PageRequest pageRequest);
}
