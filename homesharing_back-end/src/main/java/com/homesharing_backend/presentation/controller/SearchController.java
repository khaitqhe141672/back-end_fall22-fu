package com.homesharing_backend.presentation.controller;

import com.homesharing_backend.presentation.payload.request.SearchFilterRequest;
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

    @PostMapping("")
    public ResponseEntity<?> getSearchByName(@RequestBody SearchRequest nameSearch,
                                             @RequestParam("index-page") int indexPage) {
        return searchService.searchByTitlePostOrLocation(nameSearch, indexPage);
    }

    @PostMapping("/filter-search")
    public ResponseEntity<?> getSearchFilter(@RequestBody SearchFilterRequest SearchFilterRequest,
                                             @RequestParam("index-page") int indexPage) {
        return searchService.searchFilter(SearchFilterRequest, indexPage);
    }

    @PostMapping("/fill-search")
    public ResponseEntity<?> getSearch(@RequestBody SearchRequest nameSearch) {
        return searchService.getTextSearch(nameSearch);
    }

    @PostMapping("/search-by-province")
    public ResponseEntity<?> getSearchByProvince(@RequestBody SearchRequest nameSearch,
                                                 @RequestParam("index-page") int indexPage) {
        return searchService.searchByProvince(nameSearch, indexPage);
    }
}
