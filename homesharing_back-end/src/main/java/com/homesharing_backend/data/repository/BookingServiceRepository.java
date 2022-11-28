package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.dto.BookingServiceDto;
import com.homesharing_backend.data.entity.BookingServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingServiceRepository extends JpaRepository<BookingServices, Long> {

    @Query(value = "SELECT new com.homesharing_backend.data.dto.BookingServiceDto(bs.booking.id, ps.id, ps.price," +
            "s.icon, s.name) FROM BookingServices bs\n" +
            "left join Booking b on bs.booking.id = b.id\n" +
            "left join PostServices ps on bs.postServices.id = ps.id\n" +
            "left join Services s on ps.services.id = s.id where bs.booking.id = :bookingID and bs.status = 1\n" +
            "group by bs.id")
    List<BookingServiceDto> getAllBookingService(@Param("bookingID") Long bookingID);
}
