package com.homesharing_backend.presentation.controller;

import com.homesharing_backend.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/room-type")
public class RoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;

    @GetMapping("")
    public ResponseEntity<?> getAllRoomType(){
        return roomTypeService.getAllRoomType();
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insertRoomType(@RequestPart(value = "name") String name){
        return roomTypeService.insertRoomType(name);
    }
}
