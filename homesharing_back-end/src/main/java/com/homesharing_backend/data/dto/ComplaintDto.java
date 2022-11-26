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
public class ComplaintDto {

    private Long postID;
    private String title;
    private Long complaintPostID;
    private String descriptionComplaint;
    private Long reportID;
    private Long reportTypeID;
    private String fullName;
    private String username;
    private String imageUrl;
    private String nameReportType;
    private int statusComplaint;
}
