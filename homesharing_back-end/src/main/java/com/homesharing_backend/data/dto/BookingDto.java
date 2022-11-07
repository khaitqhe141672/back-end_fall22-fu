package com.homesharing_backend.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class BookingDto {

    private Long bookingID;
    private Long postID;
    private String titlePost;
    private String imagePost;
    private String nameHost;
    private String avatarHost;
    private Date startDate;
    private Date endDate;
    private int totalPerson;
    private float totalMoney;
    private int statusBooking;
    private int statusRate;
}
