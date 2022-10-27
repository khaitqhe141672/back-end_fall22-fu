package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.UtilityDto;
import com.homesharing_backend.data.entity.Host;
import com.homesharing_backend.data.entity.Utility;
import com.homesharing_backend.data.repository.HostRepository;
import com.homesharing_backend.data.repository.UtilityRepository;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.service.UtilityService;
import com.homesharing_backend.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class UtilityServiceImpl implements UtilityService {

    @Autowired
    private UtilityRepository utilityRepository;

    @Autowired
    private HostRepository hostRepository;

    @Override
    public ResponseEntity<ResponseObject> getAllUtility() {
        List<Utility> utilities = utilityRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("List utility", new HashMap<>() {
            {
                put("utilities", utilities);
            }
        }));
    }

    @Override
    public ResponseEntity<ResponseObject> insertUtility(String name) {

        Host host = hostRepository.getHostsByUser_Id(SecurityUtils.getPrincipal().getId());

        Utility utility = Utility.builder()
                .name(name)
                .host(host)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Save success utility", new HashMap<>() {
            {
                put("utility", utilityRepository.save(utility));
            }
        }));
    }

    @Override
    public ResponseEntity<JwtResponse> getAllUtilityByHostID() {

        Host host = hostRepository.getHostsByUser_Id(SecurityUtils.getPrincipal().getId());

        List<Utility> utility = utilityRepository.getUtilityByHost_Id(host.getId());

        if (Objects.isNull(utility)) {
            throw new NotFoundException("Khong co utility theo host_id nay");
        } else {

            List<UtilityDto> dtoList = new ArrayList<>();

            utility.forEach(u -> {
                UtilityDto dto = UtilityDto.builder()
                        .utilityID(u.getId())
                        .icon(u.getIcon())
                        .name(u.getName())
                        .build();

                dtoList.add(dto);
            });
            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), dtoList));
        }
    }
}
