package com.homesharing_backend.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    public String upload(MultipartFile file) throws IOException;
    public byte[] download(String fileName);
    public String delete(String fileName) throws IOException;
}
