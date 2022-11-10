package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.SearchDto;
import com.homesharing_backend.data.repository.DistrictRepository;
import com.homesharing_backend.data.repository.PostRepository;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.SearchRequest;
import com.homesharing_backend.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Override
    public ResponseEntity<ResponseObject> searchByTitlePostOrLocation(SearchRequest searchRequest) {

        if (Objects.isNull(searchRequest)) {
            throw new NotFoundException("Search null");
        } else {
            List<SearchDto> searchDtoListByTitle = postRepository.listSearchPostByTitle(searchRequest.getSearchText());

            List<SearchDto> list = new ArrayList<>();

//            if (Objects.isNull(searchDtoListByTitle)) {
//                List<SearchDto> searchDtoListByProvince = postRepository.listSearchPostByProvince(searchRequest.getSearchText());
//                if (Objects.isNull(searchDtoListByProvince)) {
//                    throw new NotFoundException("Search not find data");
//                } else {
//                    list = searchDtoListByProvince;
//                }
//            } else {
//                list = searchDtoListByTitle;
//            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("search success", new HashMap<>() {
                {
                    put("search list", searchDtoListByTitle);
                }
            }));
        }
    }
}
