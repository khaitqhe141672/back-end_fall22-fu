package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetail, Long> {

    BookingDetail getBookingDetailByBooking_Id(Long bookingID);

    @Query(value = "SELECT * FROM BookingDetail WHERE endDate= :endDate", nativeQuery = true)
    List<BookingDetail> getAllBookingDetailByEndDate(Date endDate);

    List<BookingDetail> getBookingDetailByPost_Host_Id(Long hostID);

    BookingDetail getBookingDetailById(Long id);

}
