package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.ServicesDto;
import com.homesharing_backend.data.entity.Host;
import com.homesharing_backend.data.entity.Services;
import com.homesharing_backend.data.repository.HostRepository;
import com.homesharing_backend.data.repository.ServicesRepository;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.service.ServicesService;
import com.homesharing_backend.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ServicesServiceImpl implements ServicesService {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private ServicesRepository servicesRepository;

    @Override
    public ResponseEntity<JwtResponse> getAllServiceByHost() {

        List<Services> services = servicesRepository.findAll();

        if (Objects.isNull(services)) {
            throw new NotFoundException("Khong co service lien quan den host_id nay");
        } else {
            List<ServicesDto> dtoList = new ArrayList<>();

            services.forEach(s -> {
                ServicesDto dto = ServicesDto.builder()
                        .id(s.getId())
                        .icon(s.getIcon())
                        .name(s.getName())
                        .build();

                dtoList.add(dto);
            });
            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), dtoList));
        }
    }
}
