package com.homesharing_backend.presentation.controller;

import com.homesharing_backend.presentation.payload.request.SearchRequest;
import com.homesharing_backend.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("")
    public ResponseEntity<?> getSearchByName(@RequestBody SearchRequest nameSearch) {
        return searchService.searchByTitlePostOrLocation(nameSearch);
    }
}
