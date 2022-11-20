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
@NoArgsConstructor
public class ViewBookingDto {

    private Long bookingID;
    private Long bookingDetailID;
    private Long postID;
    private String title;
    private Date startDate;
    private Date endDate;
    private float totalMoney;
    private int totalPerson;
    private float totalService;
    private String note;

    private int status;

    public ViewBookingDto(Long bookingID, Long bookingDetailID,
                          Long postID, String title, Date startDate,
                          Date endDate, float totalMoney, int totalPerson,
                          float totalService, String note, int status) {
        this.bookingID = bookingID;
        this.bookingDetailID = bookingDetailID;
        this.postID = postID;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalMoney = totalMoney;
        this.totalPerson = totalPerson;
        this.totalService = totalService;
        this.note = note;
        this.status = status;
    }
}
