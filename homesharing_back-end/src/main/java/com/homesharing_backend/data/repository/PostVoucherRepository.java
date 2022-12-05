package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.dto.PostVoucherDto;
import com.homesharing_backend.data.entity.PostVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostVoucherRepository extends JpaRepository<PostVoucher, Long> {

    List<PostVoucher> getPostVoucherByPost_Id(Long postID);

    PostVoucher getPostVoucherByIdAndPost_Id(Long id, Long postID);

    Boolean existsPostVoucherByVoucher_CodeAndPost_Id(String code, Long postID);

    List<PostVoucher> getPostVoucherByPost_IdAndStatusAndVoucher_Status(Long postID, int status, int statusVoucher);

    PostVoucher getPostVoucherByPost_IdAndVoucher_Id(Long postID, Long voucherID);

    PostVoucher getPostVoucherByPost_IdAndVoucher_Code(Long postID, String code);

    @Query(value = "SELECT new com.homesharing_backend.data.dto.PostVoucherDto(v.id, v.voucher.id, v.status) FROM PostVoucher v " +
            "WHERE v.post.id= :postID AND v.status= :status")
    List<PostVoucherDto> getAllPostVoucherByPostIDAndStatus(@Param("postID") Long postID, @Param("status") int status);
}
