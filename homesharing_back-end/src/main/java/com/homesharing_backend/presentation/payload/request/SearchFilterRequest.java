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
    private List<Long> service;
    private List<Long> roomTypeID;
    private Date startDate;
    private float minPrice;
    private float maxPrice;
    private double minStar;
    private double maxStar;
    private int statusSortPrice;
    private int numberOfGuest;
}
