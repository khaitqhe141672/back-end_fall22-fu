package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    Optional<Rate> findAllByBookingDetail_Post_Id(Long postID);
}
