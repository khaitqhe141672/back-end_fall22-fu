package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.ProvinceDto;
import com.homesharing_backend.data.entity.Province;
import com.homesharing_backend.data.repository.ProvinceRepository;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    private ProvinceRepository provinceRepository;

    @Override
    public ResponseEntity<JwtResponse> getRecommendedPlaces() {

        List<Province> provinceList = provinceRepository.getRecommendedPlacesByProvinces();

        if (provinceList.isEmpty()) {
            throw new NotFoundException("không có dữ liệu");
        } else {

            List<ProvinceDto> dtoList = new ArrayList<>();

            provinceList.forEach(p ->{
                ProvinceDto dto = ProvinceDto.builder()
                        .provinceID(p.getId())
                        .provinceName(p.getName())
                        .imageUrl(p.getImageUrl())
                        .build();
                dtoList.add(dto);
            });

            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), dtoList));
        }
    }
}
