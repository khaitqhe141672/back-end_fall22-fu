package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Booking getBookingById(Long id);

    List<Booking> getBookingByCustomer_Id(Long customerID);

}
