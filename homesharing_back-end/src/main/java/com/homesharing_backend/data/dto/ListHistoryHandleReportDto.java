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
public class ListHistoryHandleReportDto {

    private Long reportPostID;
    private int statusHistory;
    private String description;
    private String reportTypeName;
    private Long reportTypeID;
}
