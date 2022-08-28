package fpt.edu.backendfall22fu.service;

import fpt.edu.backendfall22fu.presentation.payload.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface Test {

    ResponseEntity<ResponseObject> updateFile(MultipartFile file);
}
