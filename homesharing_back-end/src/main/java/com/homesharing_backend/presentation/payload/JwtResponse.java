package com.homesharing_backend.presentation.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class JwtResponse {

    private String message;
//    private Objects object;
}
