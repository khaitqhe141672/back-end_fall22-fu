package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.*;
import com.homesharing_backend.data.entity.*;
import com.homesharing_backend.data.repository.*;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.SearchFilterRequest;
import com.homesharing_backend.presentation.payload.request.SearchRequest;
import com.homesharing_backend.service.PostService;
import com.homesharing_backend.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private PostVoucherRepository postVoucherRepository;

    @Autowired
    private PostUtilityRepository postUtilityRepository;

    @Autowired
    private PostServiceRepository postServiceRepository;

    @Override
    public ResponseEntity<ResponseObject> searchByTitlePostOrLocation(SearchRequest searchRequest, int indexPage) {

        int size = 9;
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

        int size = 9;
        int page = indexPage - 1;

        List<SearchDto> listResult = new ArrayList<>();

        if (searchFilterRequest.getTypeSearch() == 1) {
            List<SearchDto> searchDtoListByTitle =
                    postRepository.listSearchPostByTitleFilter(searchFilterRequest.getTextSearch());


            if (Objects.isNull(searchDtoListByTitle)) {
                throw new NotFoundException("search khong co data");
            } else {
                listResult = searchDtoListByTitle;
            }
//            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("search success", new HashMap<>() {
//                {
//                    put("searchList", list);
////                    put("sizePage", searchDtoListByTitle.getTotalPages());
//                }
//            }));
        } else if (searchFilterRequest.getTypeSearch() == 2) {
            List<Province> province = provinceRepository.searchAllProvinceByName(searchFilterRequest.getTextSearch());

            if (Objects.isNull(province)) {
                throw new NotFoundException("khong co tinh thanh nay");
            } else {

                //List<District> list = districtRepository.findDistrictByProvince_Id(province.getId());

                List<Long> districtID = new ArrayList<>();

                province.forEach(p -> {
                    List<District> list = districtRepository.findDistrictByProvince_Id(p.getId());
                    list.forEach(l -> {
                        districtID.add(l.getId());
                    });
                });

                List<SearchDto> searchDtoPage =
                        postRepository.listSearchPostByProvincesFilter(districtID);

                List<SearchDto> searchDtoListByProvince = new ArrayList<>();

                if (Objects.isNull(searchDtoPage)) {
                    throw new NotFoundException("search khong co data");
                } else {

                    listResult = searchDtoListByProvince;
                }
//                    searchDtoPage.forEach(s -> {
//                        SearchDto dto = SearchDto.builder()
//                                .postID(s.getPostID())
//                                .title(s.getTitle())
//                                .address(s.getAddress())
//                                .imageUrl(s.getImageUrl())
//                                .price(s.getPrice())
//                                .fullName(s.getFullName())
//                                .nameVoucher(s.getNameVoucher())
//                                .typeAccount(s.getTypeAccount())
//                                .avgStar(s.getAvgStar())
//                                .build();
//                        searchDtoListByProvince.add(dto);
//                    });
//                }
//                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("search success", new HashMap<>() {
//                    {
//                        put("listPost", searchDtoListByProvince);
//                        put("sizePage", searchDtoPage.getTotalPages());
//                    }
//                }));
            }
        } else {
            List<SearchDto> searchDtos = postRepository.getSearchFilter();

            List<SearchDto> list = new ArrayList<>();

            if (Objects.isNull(searchDtos)) {
                throw new NotFoundException("search khong co data");
            } else {

                listResult = searchDtos;
//            if (searchFilterRequest.getStatusSortPrice() == 1) {
//                searchDtos.stream().sorted(Comparator.comparing(SearchDto::getPrice));
//            } else if (searchFilterRequest.getStatusSortPrice() == 2) {
//                searchDtos.stream().sorted(Comparator.comparing(SearchDto::getPrice).reversed());
//            }


            }
        }
        List<SearchDto> list = new ArrayList<>();
        listResult.forEach(s -> {
            List<PostUtilityDto> utilityDtoList =
                    postUtilityRepository.getAllPostVoucherDTOByPostIDAndStatus(s.getPostID(), 1);
            List<PostServiceDto> serviceDtoList =
                    postServiceRepository.getAllPostServiceByPostIDAndStatus(s.getPostID(), 1);
            List<PostVoucherDto> postVoucherDtoList =
                    postVoucherRepository.getAllPostVoucherByPostIDAndStatus(s.getPostID(), 1);

            SearchDto dto = SearchDto.builder()
                    .postID(s.getPostID())
                    .title(s.getTitle())
                    .address(s.getAddress())
                    .imageUrl(s.getImageUrl())
                    .price(s.getPrice())
                    .fullName(s.getFullName())
                    .nameVoucher(s.getNameVoucher())
                    .typeAccount(s.getTypeAccount())
                    .provinceID(s.getProvinceID())
                    .utilityDtoList(utilityDtoList)
                    .serviceDtoList(serviceDtoList)
                    .numberOfGuest(s.getNumberOfGuest())
                    .postVoucherDtoList(postVoucherDtoList)
                    .typeRoomID(s.getTypeRoomID())
                    .build();

            if (Objects.isNull(s.getAvgStar())) {
                dto.setAvgStar(0.0);
            } else {
                dto.setAvgStar(s.getAvgStar());
            }

            list.add(dto);
        });

        List<SearchDto> saveSearch = list.stream().filter(searchDto
                -> searchDto.getNumberOfGuest() >= searchFilterRequest.getNumberOfGuest()
                && searchDto.getAvgStar() >= searchFilterRequest.getStatusStar()).collect(Collectors.toList());

//             && searchFilterRequest.getMinPrice() <= searchDto.getPrice()
//                    && searchDto.getPrice() <= searchFilterRequest.getMaxPrice()
//                    && searchDto.getTypeRoomID() == searchFilterRequest.getRoomTypeID()

        List<SearchDto> tempSearch = new ArrayList<>();
        for (SearchDto s : saveSearch) {
            if (searchFilterRequest.getStatusVoucher() == 1) {
                if (!s.getPostVoucherDtoList().isEmpty()) {
                    tempSearch.add(s);
                }
            } else if (searchFilterRequest.getStatusVoucher() == 2) {
                if (s.getPostVoucherDtoList().isEmpty()) {
                    tempSearch.add(s);
                }
            } else {
                tempSearch.add(s);
            }
        }

        List<Long> postServices = new ArrayList<>();

        if (!searchFilterRequest.getService().isEmpty()) {
            postServices = postServiceRepository.getAllPostServicesByListID(searchFilterRequest.getService());
            System.out.println(postServices.size());
        } else {
            postServices = postServiceRepository.getAllPostServices();
        }

        List<Long> postList = new ArrayList<>();
        List<SearchDto> tempSearch1 = new ArrayList<>();

        for (SearchDto dto : tempSearch) {
            for (Long id : postServices) {
                if (dto.getPostID() == id) {
                    tempSearch1.add(dto);
                    System.out.println(dto.getPostID() + " -  3 - " + id);
                }
            }
        }


        System.out.println("1 - " + tempSearch1.size());

        List<SearchDto> resultSearch = new ArrayList<>();

        if (!Objects.isNull(searchFilterRequest.getStartDate())) {
            System.out.println(searchFilterRequest.getStartDate());
            postList = postRepository.getAllSearchByDate(searchFilterRequest.getStartDate());
            System.out.println(" s " + postList.size());

            if (!postList.isEmpty()) {
                for (SearchDto dto : tempSearch1) {
                    for (Long id : postList) {
                        if (dto.getPostID() != id) {
                            System.out.println(dto.getPostID() + " - " + id);
                            resultSearch.add(dto);
                        } else {
                            System.out.println(dto.getPostID() + " - 1 - " + id);
                        }
                    }
                }
            } else {
                resultSearch = tempSearch1;
            }
        } else {
            resultSearch = tempSearch1;
        }


        List<SearchDto> sort = new ArrayList<>();

        if (searchFilterRequest.getStatusSortPrice() == 1) {
            System.out.println(searchFilterRequest.getStatusSortPrice());
            sort = resultSearch.stream().sorted(Comparator.comparing(SearchDto::getPrice)).collect(Collectors.toList());
        } else if (searchFilterRequest.getStatusSortPrice() == 2) {
            System.out.println(searchFilterRequest.getStatusSortPrice());
            sort = resultSearch.stream().sorted(Comparator.comparing(SearchDto::getPrice).reversed()).collect(Collectors.toList());
        } else {
            sort = resultSearch;
        }

        List<SearchDto> province = new ArrayList<>();

        if (searchFilterRequest.getProvinceID() == 0) {
            province = sort;
        } else {
            province = sort.stream().filter(searchDto
                    -> searchDto.getProvinceID() == searchFilterRequest.getProvinceID()).collect(Collectors.toList());
        }

        List<SearchDto> roomType = new ArrayList<>();

        if (searchFilterRequest.getRoomTypeID() == 0) {
            roomType = province;
        } else {
            roomType = province.stream().filter(searchDto
                    -> searchDto.getTypeRoomID() == searchFilterRequest.getRoomTypeID()).collect(Collectors.toList());
        }

        List<SearchDto> price = new ArrayList<>();

        if (searchFilterRequest.getMinPrice() == 0 || searchFilterRequest.getMaxPrice() == 0) {
            price = roomType;
        } else {
            price = roomType.stream().filter(searchDto
                    -> searchDto.getPrice() >= searchFilterRequest.getMinPrice()
                    && searchDto.getPrice() <= searchFilterRequest.getMaxPrice()).collect(Collectors.toList());
        }

        if (price.isEmpty()) {
            throw new NotFoundException("khong co data search");
        } else {

            Set resultSearchFilter = price.stream()
                    .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(SearchDto::getPostID))));

            List<SearchDto> temp = resultSearchFilter.stream().toList();

            int totalSearch = temp.size();

            int totalPage = (totalSearch % 9 == 0) ? totalSearch / 9 : totalSearch / 9 + 1;

            List<SearchDto> finalSort = temp.stream()
                    .skip(page)
                    .limit(9)
                    .collect(Collectors.toList());

            List<ViewSearchDto> viewSearchDtoList = new ArrayList<>();

            finalSort.forEach(v -> {
                ViewSearchDto dto = ViewSearchDto.builder()
                        .postID(v.getPostID())
                        .title(v.getTitle())
                        .address(v.getAddress())
                        .price(v.getPrice())
                        .imageUrl(v.getImageUrl())
                        .nameVoucher(v.getNameVoucher())
                        .avgStar(v.getAvgStar())
                        .typeAccount(v.getTypeAccount())
                        .numberOfGuest(v.getNumberOfGuest())
                        .build();
                viewSearchDtoList.add(dto);
            });
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("search success", new HashMap<>() {
                {
                    put("searchList", viewSearchDtoList);
                    put("sizePage", totalPage);
                }
            }));
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

    @Override
    public ResponseEntity<ResponseObject> searchByProvince(SearchRequest searchRequest, int indexPage) {

        int size = 9;
        int page = indexPage - 1;

        List<Province> province = provinceRepository.searchAllProvinceByName(searchRequest.getSearchText());

        if (Objects.isNull(province)) {
            throw new NotFoundException("khong co tinh thanh nay");
        } else {

            //List<District> list = districtRepository.findDistrictByProvince_Id(province.getId());

            List<Long> districtID = new ArrayList<>();

            province.forEach(p -> {
                List<District> list = districtRepository.findDistrictByProvince_Id(p.getId());
                list.forEach(l -> {
                    districtID.add(l.getId());
                });
            });

            Page<SearchDto> searchDtoPage =
                    postRepository.listSearchPostByProvinces(districtID, PageRequest.of(page, size));

            List<SearchDto> searchDtoListByProvince = new ArrayList<>();

            if (Objects.isNull(searchDtoPage)) {
                throw new NotFoundException("search khong co data");
            } else {
                searchDtoPage.forEach(s -> {
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
                    searchDtoListByProvince.add(dto);
                });
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("search success", new HashMap<>() {
                {
                    put("listPost", searchDtoListByProvince);
                    put("sizePage", searchDtoPage.getTotalPages());
                }
            }));
        }
    }
}
