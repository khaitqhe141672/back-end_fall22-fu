package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.entity.RoomType;
import com.homesharing_backend.data.repository.RoomTypeRepository;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class RoomTypeServiceImpl implements RoomTypeService {

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Override
    public ResponseEntity<ResponseObject> getAllRoomType() {

        List<RoomType> roomTypes = roomTypeRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("List room_type", new HashMap<>() {
            {
                put("roomTypes", roomTypes);
            }
        }));
    }

    @Override
    public ResponseEntity<ResponseObject> insertRoomType(String name) {

        RoomType roomType = RoomType.builder()
                .name(name)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Save success room-type", new HashMap<>() {
            {
                put("room-type", roomTypeRepository.save(roomType));
            }
        }));
    }
}
