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
public class ReportDto {

    private Long reportID;
    private String fullName;
    private String username;
    private String imageUrl;
    private String description;
    private Long reportTypeID;
    private String nameReportType;
    private int status;
    private String descriptionRate;
    private int pointRate;
}
