package com.homesharing_backend.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class ViewSearchDto {

    private Long postID;
    private String title;
    private String address;
    private float price;
    private String imageUrl;
    private String nameVoucher;
    private Double avgStar;
    private int typeAccount;
    private int numberOfGuest;
}
