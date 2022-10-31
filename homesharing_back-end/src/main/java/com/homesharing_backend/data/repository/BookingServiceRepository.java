package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.BookingServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingServiceRepository extends JpaRepository<BookingServices, Long> {
}
