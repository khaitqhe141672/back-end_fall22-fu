package com.homesharing_backend.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
@NoArgsConstructor
public class PostTopRateDto {

    private Long id;
    private String imageUrl;
    private Double avgRate;
    private String title;
    private float price;
    private String address;

    public PostTopRateDto(Long id, String imageUrl,
                          Double avgRate, String title,
                          float price, String address) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.avgRate = avgRate;
        this.title = title;
        this.price = price;
        this.address = address;
    }

    public PostTopRateDto(Long id, String imageUrl, String title) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
    }
}
