package com.homesharing_backend.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Data
@Builder

public class PostVoucherDto {

    private Long voucherID;
    private String description;
    private int dueDay;
    private String nameVoucher;
    private int percent;
    private Date startDate;
    private Date endDate;
    private int status;

}
