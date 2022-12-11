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
public class BookingServiceDto {

    private Long bookingID;
    private Long postServiceID;
    private float priceService;
    private String iconService;
    private String nameService;
    private int status;

    public BookingServiceDto(Long bookingID, Long postServiceID,
                             float priceService, String iconService,
                             String nameService, int status) {
        this.bookingID = bookingID;
        this.postServiceID = postServiceID;
        this.priceService = priceService;
        this.iconService = iconService;
        this.nameService = nameService;
        this.status = status;
    }
}
