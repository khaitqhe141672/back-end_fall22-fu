package com.homesharing_backend.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
public class DashboardBookingDto {

    private String title;
    private Long totalBooking;

    public DashboardBookingDto(String title, Long totalBooking) {
        this.title = title;
        this.totalBooking = totalBooking;
    }
}
