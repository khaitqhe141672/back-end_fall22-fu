package com.homesharing_backend.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
@NoArgsConstructor
public class SearchDto {

    private Long postID;
    private String title;
    private String address;
    private float price;
    private String imageUrl;
    private String nameVoucher;
    private Double avgStar;
    private int typeAccount;
    private String fullName;
    private Long provinceID;
    private int numberOfGuest;
    private List<PostServiceDto> serviceDtoList;
    private List<PostUtilityDto> utilityDtoList;
    private List<PostVoucherDto> postVoucherDtoList;


    public SearchDto(Long postID, String title, String address, float price,
                     String imageUrl, String nameVoucher, Double avgStar,
                     int typeAccount, String fullName, Long provinceID, int numberOfGuest,
                     List<PostServiceDto> serviceDtoList, List<PostUtilityDto> utilityDtoList,
                     List<PostVoucherDto> postVoucherDtoList) {
        this.postID = postID;
        this.title = title;
        this.address = address;
        this.price = price;
        this.imageUrl = imageUrl;
        this.nameVoucher = nameVoucher;
        this.avgStar = avgStar;
        this.typeAccount = typeAccount;
        this.fullName = fullName;
        this.provinceID = provinceID;
        this.numberOfGuest = numberOfGuest;
        this.serviceDtoList = serviceDtoList;
        this.utilityDtoList = utilityDtoList;
        this.postVoucherDtoList = postVoucherDtoList;
    }

    public SearchDto(Long postID, String title, String address,
                     float price, String imageUrl, String nameVoucher,
                     Double avgStar, int typeAccount) {
        this.postID = postID;
        this.title = title;
        this.address = address;
        this.price = price;
        this.imageUrl = imageUrl;
        this.nameVoucher = nameVoucher;
        this.avgStar = avgStar;
        this.typeAccount = typeAccount;
    }

    public SearchDto(Long postID, String title, String address,
                     float price, String imageUrl, String nameVoucher,
                     Double avgStar, int typeAccount, String fullName,
                     Long provinceID, int numberOfGuest) {
        this.postID = postID;
        this.title = title;
        this.address = address;
        this.price = price;
        this.imageUrl = imageUrl;
        this.nameVoucher = nameVoucher;
        this.avgStar = avgStar;
        this.typeAccount = typeAccount;
        this.fullName = fullName;
        this.provinceID = provinceID;
        this.numberOfGuest = numberOfGuest;
    }
}
