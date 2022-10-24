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
public class UserDto {

    private Long userID;
    private String email;
    private String username;
    private Long userDetailID;
    private String urlImage;
    private Date dob;
    private String fullName;
    private String mobile;
    private String address;
    private int status;
    private String role;
}
