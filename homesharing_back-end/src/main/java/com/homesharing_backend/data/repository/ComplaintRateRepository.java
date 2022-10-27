package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.ComplaintRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRateRepository extends JpaRepository<ComplaintRate, Long> {

    ComplaintRate getComplaintRateById(Long id);
}
