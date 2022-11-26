package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.FillSearchDto;
import com.homesharing_backend.data.dto.SearchDto;
import com.homesharing_backend.data.entity.Post;
import com.homesharing_backend.data.entity.Province;
import com.homesharing_backend.data.repository.DistrictRepository;
import com.homesharing_backend.data.repository.PostRepository;
import com.homesharing_backend.data.repository.ProvinceRepository;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.SearchFilterRequest;
import com.homesharing_backend.presentation.payload.request.SearchRequest;
import com.homesharing_backend.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Override
    public ResponseEntity<ResponseObject> searchByTitlePostOrLocation(SearchRequest searchRequest, int indexPage) {

        int size = 10;
        int page = indexPage - 1;

        if (Objects.isNull(searchRequest)) {
            throw new NotFoundException("Search null");
        } else {

            Page<SearchDto> searchDtoListByTitle = postRepository.listSearchPostByTitle(searchRequest.getSearchText(), PageRequest.of(page, size));

            List<SearchDto> list = new ArrayList<>();

            if (Objects.isNull(searchDtoListByTitle)) {
                throw new NotFoundException("search khong co data");
            } else {
                searchDtoListByTitle.forEach(s -> {
                    SearchDto dto = SearchDto.builder()
                            .postID(s.getPostID())
                            .title(s.getTitle())
                            .address(s.getAddress())
                            .imageUrl(s.getImageUrl())
                            .price(s.getPrice())
                            .fullName(s.getFullName())
                            .nameVoucher(s.getNameVoucher())
                            .typeAccount(s.getTypeAccount())
                            .avgStar(s.getAvgStar())
                            .build();
                    list.add(dto);
                });
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("search success", new HashMap<>() {
                {
                    put("searchList", list);
                    put("sizePage", searchDtoListByTitle.getTotalPages());
                }
            }));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> searchFilter(SearchFilterRequest searchFilterRequest, int indexPage) {

        int size = 10;
        int page = indexPage - 1;

        Page<SearchDto> searchDtos = postRepository.getSearchFilter(searchFilterRequest.getVoucherPercent(),
                searchFilterRequest.getMinPrice(), searchFilterRequest.getMaxPrice(), searchFilterRequest.getRoomTypeID(),
                searchFilterRequest.getNumberOfGuest(), searchFilterRequest.getRoomTypeID(), searchFilterRequest.getMinStar(),
                searchFilterRequest.getMaxStar(), searchFilterRequest.getStartDate(), searchFilterRequest.getProvinceID(), PageRequest.of(page, size));

        List<SearchDto> list = new ArrayList<>();

        if (Objects.isNull(searchDtos)) {
            throw new NotFoundException("search khong co data");
        } else {

            if (searchFilterRequest.getStatusSortPrice() == 1) {
                searchDtos.stream().sorted(Comparator.comparing(SearchDto::getPrice));
            } else if (searchFilterRequest.getStatusSortPrice() == 2) {
                searchDtos.stream().sorted(Comparator.comparing(SearchDto::getPrice).reversed());
            }

            searchDtos.forEach(s -> {
                SearchDto dto = SearchDto.builder()
                        .postID(s.getPostID())
                        .title(s.getTitle())
                        .address(s.getAddress())
                        .imageUrl(s.getImageUrl())
                        .price(s.getPrice())
                        .fullName(s.getFullName())
                        .nameVoucher(s.getNameVoucher())
                        .typeAccount(s.getTypeAccount())
                        .avgStar(s.getAvgStar())
                        .build();
                list.add(dto);
            });
            if (Objects.isNull(list)) {
                throw new NotFoundException("khong co data search");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("search success", new HashMap<>() {
                    {
                        put("searchList", list);
                        put("sizePage", searchDtos.getTotalPages());
                    }
                }));
            }
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getTextSearch(SearchRequest searchRequest) {

        List<FillSearchDto> postList = postRepository.searchPostByTitle(searchRequest.getSearchText(), PageRequest.of(0, 5));

        List<Province> provinceList = provinceRepository.getSearchNameProvince(searchRequest.getSearchText());

        List<FillSearchDto> list = new ArrayList<>();

        postList.forEach(p -> {
            FillSearchDto dto = FillSearchDto.builder()
                    .province(p.getProvince())
                    .urlImage(p.getUrlImage())
                    .postID(p.getPostID())
                    .price(p.getPrice())
                    .title(p.getTitle())
                    .build();
            list.add(dto);
        });

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("search success", new HashMap<>() {
            {
                put("listPost", list);
                put("listProvince", provinceList);
            }
        }));
    }
}
