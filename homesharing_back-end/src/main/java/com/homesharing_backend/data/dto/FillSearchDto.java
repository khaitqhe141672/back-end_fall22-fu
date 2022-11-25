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
public class FillSearchDto {

    private Long postID;
    private String title;
    private String urlImage;
    private float price;
    private String province;

    public FillSearchDto(Long postID, String title,
                         String urlImage, float price, String province) {
        this.postID = postID;
        this.title = title;
        this.urlImage = urlImage;
        this.price = price;
        this.province = province;
    }
}
