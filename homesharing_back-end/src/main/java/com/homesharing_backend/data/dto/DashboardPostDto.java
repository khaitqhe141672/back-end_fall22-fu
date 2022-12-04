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
public class DashboardPostDto {

    private int month;
    private int totalPost;

    public DashboardPostDto(int month, int totalPost) {
        this.month = month;
        this.totalPost = totalPost;
    }
}
