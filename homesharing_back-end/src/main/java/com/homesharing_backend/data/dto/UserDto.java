package com.homesharing_backend.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonInclude(Include.NON_NULL)
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class UserDto {
     private String username;
     private String email;
     private String firstName;
     private String lastName;
     private String role;
}
