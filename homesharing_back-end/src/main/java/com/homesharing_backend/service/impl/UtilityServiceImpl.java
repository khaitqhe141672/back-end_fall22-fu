package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.entity.Utility;
import com.homesharing_backend.data.repository.UtilityRepository;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UtilityServiceImpl implements UtilityService {

    @Autowired
    private UtilityRepository utilityRepository;


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

        Utility utility = Utility.builder()
                .name(name)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Save success utility", new HashMap<>() {
            {
                put("utility", utilityRepository.save(utility));
            }
        }));
    }
}
