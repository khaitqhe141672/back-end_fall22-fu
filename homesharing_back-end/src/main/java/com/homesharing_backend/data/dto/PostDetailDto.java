package com.homesharing_backend.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class PostDetailDto {

    private Long postDetailID;
    private Long postID;
    private String title;
    private float price;
    private Date createDate;
    private String hostName;
    private String mobileHost;
    private String imageUrlHost;
    private String address;
    private String description;
    private int guestNumber;
    private int numberOfBathrooms;
    private int numberOfBedrooms;
    private int numberOfBeds;
    private float serviceFee;
    private String roomTypeName;
    private List<PostImageDto> imageDtoList;
    private List<PostUtilityDto> postUtilityDtoList;
    private DistrictDto districtDto;
    private VoucherDto voucherDto;
    private Double avgRate;
    private int status;
}