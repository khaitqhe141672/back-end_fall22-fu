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
public class PostServiceDto {

    private Long postServiceID;
    private String iconService;
    private String nameService;
    private float price;
    private Long serviceID;
    private int status;

    public PostServiceDto(Long postServiceID, String iconService,
                          String nameService, float price, Long serviceID, int status) {
        this.postServiceID = postServiceID;
        this.iconService = iconService;
        this.nameService = nameService;
        this.price = price;
        this.serviceID = serviceID;
        this.status = status;
    }

    public PostServiceDto(Long postServiceID, String nameService, Long serviceID, int status) {
        this.postServiceID = postServiceID;
        this.nameService = nameService;
        this.serviceID = serviceID;
        this.status = status;
    }
}
