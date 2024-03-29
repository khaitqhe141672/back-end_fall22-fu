package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.dto.ProvinceDto;
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

    @Query(value = "SELECT * FROM Province p WHERE p.name LIKE %:name%", nativeQuery = true)
    List<Province> searchAllProvinceByName(@Param("name") String name);

    @Query(value = "SELECT new com.homesharing_backend.data.dto.ProvinceDto( pr.id, pr.name, pr.imageUrl) FROM Province pr \n" +
            "left join District d on pr.id = d.province.id \n" +
            "left join PostDetail pd on d.id = pd.district.id " +
            "left join Post p on pd.post.id = p.id where p.status= 1 \n" +
            "group by pr.id order by count(pd.id) desc")
    List<ProvinceDto> getTopProvince();
}
