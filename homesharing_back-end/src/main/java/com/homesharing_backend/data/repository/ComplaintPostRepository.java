package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.ComplaintPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintPostRepository extends JpaRepository<ComplaintPost, Long> {

    ComplaintPost getComplaintPostById(Long id);

    Page<ComplaintPost> getComplaintPostByHost_Id(Long hostID, PageRequest pageRequest);

    ComplaintPost getComplaintPostByIdAndPost_Id(Long id, Long postID);

    ComplaintPost getComplaintPostByHistoryHandleReportPost_Id(Long id);
}
