package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Booking getBookingById(Long id);

    Page<Booking> getBookingByCustomer_Id(Long customerID, PageRequest pageRequest);

    int countBookingByStatus(int status);

}
