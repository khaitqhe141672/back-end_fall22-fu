package com.homesharing_backend.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.sql.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
@NoArgsConstructor
public class PostDto {

    private Long postID;
    private String title;
    private String urlImage;
    private Integer status;
    private Integer statusPaymentPackage;
    private Date endDate;
    private Date startDate;
    private Double avgRate;

    public PostDto(Long postID, String title, String urlImage, Integer status, Integer statusPaymentPackage,
                   Date endDate, Date startDate, Double avgRate) {
        this.postID = postID;
        this.title = title;
        this.urlImage = urlImage;
        this.status = status;
        this.statusPaymentPackage = statusPaymentPackage;
        this.endDate = endDate;
        this.startDate = startDate;
        this.avgRate = avgRate;
    }
}
