package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.PostPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostPaymentRepository extends JpaRepository<PostPayment, Long> {

    @Query(value = "SELECT * FROM demo.post_payment where post_id = :postID Order by id desc limit 1", nativeQuery = true)
    PostPayment getTimePost(Long postID);

    PostPayment getPostPaymentByPost_IdAndStatus(Long postID, int status);

    PostPayment getPostPaymentByIdAndPost_IdAndPaymentPackage_Id(Long id, Long postID, Long paymentPackageID);
}
