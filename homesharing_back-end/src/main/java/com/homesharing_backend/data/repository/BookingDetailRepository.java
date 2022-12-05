package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.dto.DashboardBookingDto;
import com.homesharing_backend.data.dto.DateBookingDto;
import com.homesharing_backend.data.entity.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "SELECT new com.homesharing_backend.data.dto.DateBookingDto(bd.startDate, bd.endDate) FROM BookingDetail bd " +
            "LEFT JOIN Booking b ON bd.booking.id = b.id " +
            "WHERE bd.post.id= :postID AND (b.status = 2 OR b.status = 3) " +
            "GROUP BY bd.id")
    List<DateBookingDto> getAllBookingByPostID(@Param("postID") Long postID);

    @Query("SELECT new com.homesharing_backend.data.dto.DashboardBookingDto(p.id, count(p.id)) FROM BookingDetail bd " +
            "left join Booking b on bd.booking.id = b.id " +
            "left join Post p on bd.post.id = p.id where p.host.id= :hostID and b.status= :status \n" +
            "group by p.id")
    List<DashboardBookingDto> totalBookingByHost(@Param("hostID") Long hostID, @Param("status") int status);
}
