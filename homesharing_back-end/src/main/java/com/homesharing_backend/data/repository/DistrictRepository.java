package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

    Optional<District> findDistrictById(Long id);

    List<District> findDistrictByProvince_Id(Long provinceID);
}
