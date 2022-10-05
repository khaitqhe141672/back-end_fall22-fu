package com.homesharing_backend.presentation.payload;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class JwtResponse {

    private String message;
    private Object object;
}
