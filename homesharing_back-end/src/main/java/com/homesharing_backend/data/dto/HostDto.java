package com.homesharing_backend.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class HostDto {

    private Long hostID;
    private Long userID;
    private Long userDetailID;
    private String username;
    private String email;
    private String urlImage;
    private String roleName;
    private Date createDate;
    private Date dob;
    private String fullName;
    private String mobile;
    private String address;
    private Long totalFollower;
    private int status;
}
