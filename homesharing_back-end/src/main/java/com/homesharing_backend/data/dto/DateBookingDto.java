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
public class DateBookingDto {

    private Date startDate;
    private Date endDate;

    public DateBookingDto(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
