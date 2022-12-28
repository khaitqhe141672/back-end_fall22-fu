package com.homesharing_backend.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class ViewDetailRateDto {

    private Long customerID;
    private String username;
    private String fullName;
    private String urlImage;
    private int star;
    private int totalLike;
    private int totalDislike;
    private Long rateID;
    private String comment;
    private Date dateRate;
    private int statusRate;
    private String descriptionReport;
    private int statusReportRate;
    private String reportTypeName;
}
