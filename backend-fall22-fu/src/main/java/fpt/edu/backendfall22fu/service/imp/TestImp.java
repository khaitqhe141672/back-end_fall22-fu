package fpt.edu.backendfall22fu.service.imp;

import fpt.edu.backendfall22fu.presentation.payload.ResponseObject;
import fpt.edu.backendfall22fu.service.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@Service
public class TestImp implements Test {

    @Autowired
    private AWSService awsService;

    @Autowired
    TestImp(AWSService awsService) {
        this.awsService = awsService;
    }

    @Override
    public ResponseEntity<ResponseObject> updateFile(MultipartFile file) {
        try {
            String fileName = awsService.upload(file);
            if (fileName == null) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Data existed",
                        new HashMap<>() {
                            {
                                put("file", "load fail");
                            }
                        }));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Data existed",
                        new HashMap<>() {
                            {
                                put("classroom", fileName);
                            }
                        }));
            }
        } catch (Exception e) {
            return null;
        }
    }
}
