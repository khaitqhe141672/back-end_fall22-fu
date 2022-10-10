package com.homesharing_backend.presentation.controller;

import com.homesharing_backend.data.entity.Province;
import com.homesharing_backend.data.repository.ProvinceRepository;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.service.impl.AWSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/demo")
public class Test {

    private AWSService awsService;

    @Autowired
    Test(AWSService awsService) {
        this.awsService = awsService;
    }

    @Autowired
    private ProvinceRepository provinceRepository;

    @PostMapping("")
    public ResponseEntity<ResponseObject> testD(@RequestParam("file") MultipartFile file) {
        String fileName = awsService.upload(file);
        Province p = Province.builder()
                .name(fileName)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Data existed",
                new HashMap<>() {
                    {
                        put("File", fileName);
                        put("image", provinceRepository.save(p));
                    }
                }
        ));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseObject> update(@RequestParam("id") Long id,
                                                 @RequestParam("file") MultipartFile file){

        Province province = provinceRepository.getById(id);

        List<String> list = List.of(province.getName().split("/"));

        awsService.delete(list.get(3));

        String fileName = awsService.upload(file);

        Province p = Province.builder()
                .name(fileName)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Data existed",
                new HashMap<>() {
                    {
                        put("File", provinceRepository.save(p));
                    }
                }
        ));
    }
}
