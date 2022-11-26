package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {

    Province getById(Long id);

    @Query(value = "SELECT * FROM Province Limit 8", nativeQuery = true)
    List<Province> getRecommendedPlacesByProvinces();

    Province getProvincesByName(String name);

    @Query(value = "SELECT * FROM Province p WHERE p.name LIKE %:name% LIMIT 5", nativeQuery = true)
    List<Province> getSearchNameProvince(@Param("name") String name);
}
