package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.SearchFilterRequest;
import com.homesharing_backend.presentation.payload.request.SearchRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface SearchService {

    public ResponseEntity<ResponseObject> searchByTitlePostOrLocation(SearchRequest searchRequest, int indexPage);

    public ResponseEntity<ResponseObject> searchFilter(SearchFilterRequest searchFilterRequest, int indexPage);

    public ResponseEntity<ResponseObject> getTextSearch(SearchRequest searchRequest);

}
