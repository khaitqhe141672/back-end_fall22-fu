package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Long> {

    Services getServicesById(Long id);

    List<Services> getServicesByHost_Id(Long hostID);
}
