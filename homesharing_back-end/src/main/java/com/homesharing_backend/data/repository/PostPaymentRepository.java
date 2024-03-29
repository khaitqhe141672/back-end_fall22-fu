package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.dto.DashboardPaymentPostDto;
import com.homesharing_backend.data.dto.DashboardPostDto;
import com.homesharing_backend.data.dto.DashboardPostPaymentDto;
import com.homesharing_backend.data.entity.PostPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostPaymentRepository extends JpaRepository<PostPayment, Long> {

    @Query(value = "SELECT * FROM demo.post_payment where post_id = :postID Order by id desc limit 1", nativeQuery = true)
    PostPayment getTimePost(Long postID);

    PostPayment getPostPaymentByPost_IdAndStatus(Long postID, int status);

    PostPayment getPostPaymentByIdAndPost_IdAndPaymentPackage_Id(Long id, Long postID, Long paymentPackageID);

    PostPayment getPostPaymentByPost_IdAndStatusAndPaymentPackage_Id(Long postID, int status, Long paymentPackageID);

    @Query(value = "SELECT sum(pp.price) FROM demo.post_payment py " +
            "left join payment_package pp on py.payment_package_id = pp.id ", nativeQuery = true)
    int totalPricePostPayment();

    @Query(value = "SELECT new com.homesharing_backend.data.dto.DashboardPostPaymentDto(pp.id, count(pp.id)) FROM PaymentPackage pp\n" +
            "left join PostPayment py on pp.id = py.paymentPackage.id " +
            "group by pp.id")
    List<DashboardPostPaymentDto> getAllPostPayment();

    @Query(value = "SELECT  new com.homesharing_backend.data.dto.DashboardPostPaymentDto(p.id, sum(pp.price)) FROM PostPayment py " +
            "left join PaymentPackage pp on py.paymentPackage.id = pp.id " +
            "left join Post p on py.post.id = p.id where p.host.id= :hostID " +
            "group by py.id ")
    List<DashboardPostPaymentDto> getAllPostPaymentByHost(@Param("hostID") Long hostID);


    @Query(value = "SELECT new com.homesharing_backend.data.dto.DashboardPaymentPostDto(pp.post.title, sum(pg.price)) FROM PostPayment pp " +
            "LEFT JOIN Post p ON pp.post.id = p.id " +
            "LEFT JOIN PaymentPackage pg ON pp.paymentPackage.id = pg.id " +
            "WHERE p.host.id= :hostID " +
            "GROUP BY pp.post.title")
    List<DashboardPaymentPostDto> getAllPaymentByHost(@Param("hostID") Long hostID);

}
