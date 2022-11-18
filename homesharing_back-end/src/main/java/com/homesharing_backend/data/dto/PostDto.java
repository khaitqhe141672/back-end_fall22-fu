package com.homesharing_backend.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
public class PostDto {

    private Long postID;
    private String title;
    private String urlImage;
    private Long reportPostID;
    private Integer statusReportPost;
    private Integer status;
    private Integer statusPaymentPackage;
    private Date endDate;
    private Date startDate;
    private Double avgRate;

    private int statusPostPayment;

    public PostDto(Long postID, String title, String urlImage, Long reportPostID,
                   Integer statusReportPost, Integer status, Integer statusPaymentPackage,
                   Date endDate, Date startDate, Double avgRate) {
        this.postID = postID;
        this.title = title;
        this.urlImage = urlImage;
        this.reportPostID = reportPostID;
        this.statusReportPost = statusReportPost;
        this.status = status;
        this.statusPaymentPackage = statusPaymentPackage;
        this.endDate = endDate;
        this.startDate = startDate;
        this.avgRate = avgRate;
    }

    public PostDto(Long postID, String title, Integer status, Double avgRate) {
        this.postID = postID;
        this.title = title;
        this.status = status;
        this.avgRate = avgRate;
    }
}
