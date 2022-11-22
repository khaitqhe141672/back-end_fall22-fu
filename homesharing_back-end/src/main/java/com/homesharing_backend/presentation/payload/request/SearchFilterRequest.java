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

    private List<Integer> voucherPercent;
    private List<String> service;
    private List<Long> roomTypeID;
    private Date startDate;
    private Date endDate;
    private float minPrice;
    private float maxPrice;
    private int minStar;
    private int maxStar;
    private int statusSortPrice;
    private int numberOfGuest;
}
