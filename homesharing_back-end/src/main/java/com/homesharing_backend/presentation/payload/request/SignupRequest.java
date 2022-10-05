package com.homesharing_backend.presentation.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    @NotBlank
    @Size(min = 4, max = 20)
    private String username;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

//    private Set<String> role;

//    private String role;


//    @Size(max = 20)
//    private String firstName;
//
//    @Size(max = 20)
//    private String lastName;

    private String fullName;

    private String address;

    private String mobile;

    private Date dob;

}
