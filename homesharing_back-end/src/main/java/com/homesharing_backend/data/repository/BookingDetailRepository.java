package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetail, Long> {
}
