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
public class PostView {

    private Long postID;
    private String title;
    private Date startDate;
    private Date endDate;
    private int statusPost;
    private int statusPostPayment;
}
