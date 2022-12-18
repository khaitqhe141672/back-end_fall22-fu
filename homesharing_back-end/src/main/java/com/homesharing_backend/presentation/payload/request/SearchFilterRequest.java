package com.homesharing_backend.presentation.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchFilterRequest {

    private int statusVoucher;
    private List<Long> service;
    private Long roomTypeID;
    private Date startDate;
    private float minPrice;
    private float maxPrice;
    private int statusStar;
    private int statusSortPrice;
    private int numberOfGuest;
    private Long provinceID;
    private String textSearch;
    private int typeSearch;
}
