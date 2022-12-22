package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.Rate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    Page<Rate> findAllByBookingDetail_Post_IdAndStatus(Long postID, int status, PageRequest pageRequest);

    Rate getRateById(Long id);

    Boolean existsRatesById(Long id);

    Rate getRateByBookingDetail_Id(Long bookingDetailID);

    int countRateByBookingDetail_Post_Id(Long postID);

    Page<Rate> getRateByBookingDetail_Post_Id(Long postID, PageRequest pageRequest);
}
