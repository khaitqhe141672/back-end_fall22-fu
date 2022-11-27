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
public class PostUtilityDto {

    private Long postUtilityID;
    private String iconUtility;
    private String nameUtility;
    private Long utilityID;
    private int status;

    public PostUtilityDto(Long postUtilityID, String iconUtility,
                          String nameUtility, Long utilityID, int status) {
        this.postUtilityID = postUtilityID;
        this.iconUtility = iconUtility;
        this.nameUtility = nameUtility;
        this.utilityID = utilityID;
        this.status = status;
    }

    public PostUtilityDto(Long postUtilityID, String nameUtility, Long utilityID, int status) {
        this.postUtilityID = postUtilityID;
        this.nameUtility = nameUtility;
        this.utilityID = utilityID;
        this.status = status;
    }

}
