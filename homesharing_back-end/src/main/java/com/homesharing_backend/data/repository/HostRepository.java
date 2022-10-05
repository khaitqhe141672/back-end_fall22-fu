package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostRepository extends JpaRepository<Host, Long> {

    Host getHostsByUser_Username(String useName);
}
