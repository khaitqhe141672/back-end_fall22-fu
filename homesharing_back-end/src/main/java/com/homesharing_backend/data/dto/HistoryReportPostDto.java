package com.homesharing_backend.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class HistoryReportPostDto {

    private Long postID;
    private String title;
    private Long historyHandleReportPostID;
    private int statusPost;
    private int statusReportPost;
    private int totalReportPost;
    private int complaintStatus;
    private String description;
    private String typeName;
}
