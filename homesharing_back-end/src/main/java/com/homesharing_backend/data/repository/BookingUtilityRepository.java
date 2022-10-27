package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.BookingUtility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingUtilityRepository extends JpaRepository<BookingUtility, Long> {

    List<BookingUtility> findBookingUtilityByBooking_Id(Long bookingID);
}
