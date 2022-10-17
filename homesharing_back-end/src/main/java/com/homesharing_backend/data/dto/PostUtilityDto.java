package com.homesharing_backend.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class PostUtilityDto {

    private Long postUtilityID;
    private String description;
    private float price;
    private Long utilityID;
    private int status;
}
