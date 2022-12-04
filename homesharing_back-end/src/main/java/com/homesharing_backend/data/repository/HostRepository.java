package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HostRepository extends JpaRepository<Host, Long> {

    Host getHostsByUser_Username(String useName);

    Host getHostsByUser_Id(Long id);

    Host getHostsById(Long id);

    @Query(value = "SELECT count(id) FROM Host ")
    int totalHost();

    int countHostByUser_Status(@Param("status") int status);
}
