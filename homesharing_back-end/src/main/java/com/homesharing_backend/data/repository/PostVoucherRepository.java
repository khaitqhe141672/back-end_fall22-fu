package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.PostVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostVoucherRepository extends JpaRepository<PostVoucher, Long> {

    List<PostVoucher> getPostVoucherByPost_Id(Long postID);

    PostVoucher getPostVoucherByIdAndPost_Id(Long id, Long postID);

    Boolean existsPostVoucherByVoucher_CodeAndPost_Id(String code, Long postID);

    List<PostVoucher> getPostVoucherByPost_IdAndStatus(Long postID, int status);

    PostVoucher getPostVoucherByPost_IdAndVoucher_Id(Long postID, Long voucherID);

}
