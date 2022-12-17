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

    private Long postID;
    private Long price;

    public DashboardPaymentPostDto(Long postID, Long price) {
        this.postID = postID;
        this.price = price;
    }
}
