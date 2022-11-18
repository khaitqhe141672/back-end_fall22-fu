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
    private Integer typeAccount;
    private Integer totalReport;
    private int status;

    public ReportPostDto(Long postID, String title,
                         float price, String imagePostUrl,
                         String username, String imageUserUrl,
                         int typeAccount, int status) {
        this.postID = postID;
        this.title = title;
        this.price = price;
        this.imagePostUrl = imagePostUrl;
        this.username = username;
        this.imageUserUrl = imageUserUrl;
        this.typeAccount = typeAccount;
        this.status = status;
    }
}
