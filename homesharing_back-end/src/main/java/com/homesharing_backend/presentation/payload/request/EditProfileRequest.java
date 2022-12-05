package com.homesharing_backend.presentation.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditProfileRequest {

    private String fullName;
    private String mobile;
    private String address;
}
