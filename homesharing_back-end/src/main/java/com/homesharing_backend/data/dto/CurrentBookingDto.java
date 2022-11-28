package com.homesharing_backend.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class CurrentBookingDto {

    private ViewBookingDto viewBookingDto;
    private UserBookingDto userBookingDto;
    private List<BookingServiceDto> bookingServiceDtos;
}
