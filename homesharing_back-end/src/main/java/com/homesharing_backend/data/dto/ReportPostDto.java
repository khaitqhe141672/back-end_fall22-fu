package com.homesharing_backend.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.homesharing_backend.data.entity.ReportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private Integer typeAccount;
    private Integer totalReport;
    private int statusPost;

    private int statusReportPost;

    private List<ReportType> reportTypes;

    public ReportPostDto(Long postID, String title,
                         float price, String imagePostUrl,
                         String username, String imageUserUrl,
                         int typeAccount, int statusPost, int statusReportPost) {
        this.postID = postID;
        this.title = title;
        this.price = price;
        this.imagePostUrl = imagePostUrl;
        this.username = username;
        this.imageUserUrl = imageUserUrl;
        this.typeAccount = typeAccount;
        this.statusPost = statusPost;
        this.statusReportPost = statusReportPost;
    }
}
