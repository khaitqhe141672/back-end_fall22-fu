package com.homesharing_backend.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
public class DashboardPaymentPostDto {

    private String title;
    private Long price;

    public DashboardPaymentPostDto(String title, Long price) {
        this.title = title;
        this.price = price;
    }
}
