package com.homesharing_backend.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class RateDto {

    private Long rateID;
    private Long postID;
    private Long customerID;
    private String username;
    private String urlImage;
    private String comment;
    private int point;
    private Date dateRate;
    private int countLike;
    private int countDislike;
    private int status;
}
