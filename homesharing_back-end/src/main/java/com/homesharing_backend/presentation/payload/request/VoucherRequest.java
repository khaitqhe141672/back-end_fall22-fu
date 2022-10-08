package com.homesharing_backend.presentation.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherRequest {

    private String name;
    private String description;
    private int percent;
    private int dueDate;

}
