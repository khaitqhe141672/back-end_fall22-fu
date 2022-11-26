package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.ProvinceDto;
import com.homesharing_backend.data.entity.District;
import com.homesharing_backend.data.entity.Province;
import com.homesharing_backend.data.repository.DistrictRepository;
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
import java.util.Objects;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Override
    public ResponseEntity<JwtResponse> getRecommendedPlaces() {

        List<Province> provinceList = provinceRepository.getRecommendedPlacesByProvinces();

        if (provinceList.isEmpty()) {
            throw new NotFoundException("không có dữ liệu");
        } else {

            List<ProvinceDto> dtoList = new ArrayList<>();

            provinceList.forEach(p -> {
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

    @Override
    public ResponseEntity<JwtResponse> getAllProvince() {

        List<Province> provinces = provinceRepository.findAll();

        if (provinces.isEmpty()) {
            throw new NotFoundException("khong co du lieu thanh pho");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), provinces));
        }
    }

    @Override
    public ResponseEntity<JwtResponse> getAllDistrict() {

        List<District> districts = districtRepository.findAll();

        if (districts.isEmpty()) {
            throw new NotFoundException("khong co du lieu cua huyen");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), districts));
        }
    }

    @Override
    public ResponseEntity<JwtResponse> getAllDistrictByProvinceID(Long provinceID) {

        List<District> districts = districtRepository.findDistrictByProvince_Id(provinceID);

        if (Objects.isNull(districts)) {
            throw new NotFoundException("khong co du lieu cua huyen theo province_id");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), districts));
        }
    }

    @Override
    public ResponseEntity<JwtResponse> getOneProvinceByProvinceID(Long provinceID) {

        Province province = provinceRepository.getById(provinceID);

        if(Objects.isNull(province)){
            throw new NotFoundException("khong co du lieu cua thanh pho theo province_id");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), province));
        }
    }
}
