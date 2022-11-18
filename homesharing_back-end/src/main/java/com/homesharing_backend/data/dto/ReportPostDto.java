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
public class ReportPostDto {

    private Long postID;
    private String title;
    private float price;
    private String imagePostUrl;
    private String username;
    private String imageUserUrl;
    private int typeAccount;
    private int totalReport;
    private int status;
}
