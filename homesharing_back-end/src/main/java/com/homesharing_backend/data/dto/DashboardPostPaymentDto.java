package com.homesharing_backend.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
public class DashboardPostPaymentDto {

    private Long packagePaymentID;
    private Long totalPost;

    public DashboardPostPaymentDto(Long packagePaymentID, Long totalPost) {
        this.packagePaymentID = packagePaymentID;
        this.totalPost = totalPost;
    }
}
